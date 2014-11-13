package ru.bmstu.rk9.rdo.lib;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import ru.bmstu.rk9.rdo.lib.Database.TypeSize;
import ru.bmstu.rk9.rdo.lib.Tracer.TraceOutput;
import ru.bmstu.rk9.rdo.lib.Tracer.TraceType;

public class LegacyTracer
{
	LegacyTracer()
	{
		legacyResourceIndexes =
			new HashMap<Integer, HashMap<Integer, Integer>>();
		takenIds = new TreeSet<Integer>();
		initializeTypes();

		ModelStructureHelper.fillResourceTypesInfo(resourceTypesInfo);
		ModelStructureHelper.fillResultsInfo(resultsInfo);
		ModelStructureHelper.fillPatternsInfo(patternsInfo);
		ModelStructureHelper.fillDecisionPointsInfo(decisionPointsInfo);
	}

	private final HashMap<Integer, HashMap<Integer, Integer>> legacyResourceIndexes;
	private final TreeSet<Integer> takenIds;

	private final ArrayList<ResourceTypeInfo> resourceTypesInfo =
		new ArrayList<ResourceTypeInfo>();
	private final ArrayList<ResultInfo> resultsInfo =
		new ArrayList<ResultInfo>();
	private final ArrayList<DecisionPointInfo> decisionPointsInfo =
		new ArrayList<DecisionPointInfo>();
	private final ArrayList<PatternInfo> patternsInfo =
		new ArrayList<PatternInfo>();

	static private final String delimiter = " ";

	private ArrayList<TraceOutput> traceList = new ArrayList<TraceOutput>();

	public final ArrayList<TraceOutput> getTraceList()
	{
		//TODO make unmodifiable
		return traceList;
	}

	public final void saveTraceData()
	{
		final ArrayList<Database.Entry> entries =
			Simulator.getDatabase().allEntries;

		for (Database.Entry entry : entries)
		{
			final TraceOutput traceOutput = parseSerializedData(entry);
			if (traceOutput != null)
				traceList.add(traceOutput);
		}
	}

	private final TraceOutput parseSerializedData(final Database.Entry entry)
	{
		final Database.EntryType type =
			Database.EntryType.values()[entry.header.get(
				TypeSize.Internal.ENTRY_TYPE_OFFSET)];
		switch(type)
		{
		//TODO implement the rest of EntryTypes
		case RESOURCE:
			return parseResourceEntry(entry);
		case RESULT:
			return parseResultEntry(entry);
		case PATTERN:
			return parsePatternEntry(entry);
		default:
			return null;
		}
	}

  /*――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――/
 /                          PARSING RESOURCE ENTRIES                         /
/――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――*/

	private final TraceOutput parseResourceEntry(final Database.Entry entry)
	{
		final ByteBuffer resourceHeader = entry.header;

		Tracer.prepareBufferForReading(resourceHeader);

		final double time = resourceHeader.getDouble();
		Tracer.skipPart(resourceHeader, TypeSize.BYTE);
		final TraceType traceType;
		byte entryType = resourceHeader.get();
		final int typeNum = resourceHeader.getInt();
		final int resNum = resourceHeader.getInt();

		int legacyId;
		switch(entryType)
		{
		case 0:
			//TODO resources created before model start should have
			//RK converter status instead of RC
			traceType = TraceType.RESOURCE_CREATE;
			if (legacyResourceIndexes.get(typeNum).get(resNum) == null)
				legacyId = getNewId(typeNum, resNum);
			else
				legacyId = legacyResourceIndexes.get(typeNum).get(resNum);
			break;
		case 1:
			traceType = TraceType.RESOURCE_ERASE;
			legacyId = legacyResourceIndexes.get(typeNum).get(resNum);
			freeId(typeNum, resNum);
			break;
		case 2:
			traceType = TraceType.RESOURCE_KEEP;
			legacyId = legacyResourceIndexes.get(typeNum).get(resNum);
			break;
		default:
			return null;
		}

		final String headerLine =
			new StringJoin(delimiter)
			.add(traceType.toString())
			.add(checkIntegerValuedReal((time)))
			.add(String.valueOf(typeNum + 1))
			.add(String.valueOf(legacyId))
			.getString();

		//TODO fix when resource parameters are also serialized on erase
		if (traceType == TraceType.RESOURCE_ERASE)
		{
			return new TraceOutput(traceType, headerLine);
		}

		final ResourceTypeInfo typeInfo = resourceTypesInfo.get(typeNum);

		return
			new TraceOutput(
				traceType,
				new StringJoin(delimiter)
					.add(headerLine)
					.add(parseResourceParameters(entry.data, typeInfo))
					.getString()
			);
	}

	private final String parseResourceParameters(
		final ByteBuffer resourceData,
		final ResourceTypeInfo typeInfo
	)
	{
		final StringJoin stringBuilder = new StringJoin(delimiter);

		Tracer.prepareBufferForReading(resourceData);

		for (int paramNum = 0; paramNum < typeInfo.numberOfParameters; paramNum++)
		{
			//TODO trace arrays when they are implemented
			switch(typeInfo.paramTypes.get(paramNum).type)
			{
			case INTEGER:
				stringBuilder.add(String.valueOf(resourceData.getInt()));
				break;
			case REAL:
				stringBuilder.add(checkIntegerValuedReal(resourceData.getDouble()));
				break;
			case BOOLEAN:
				stringBuilder.add(legacyBooleanString(resourceData.get() != 0));
				break;
			case ENUM:
				stringBuilder.add(String.valueOf(resourceData.getShort()));
				break;
			case STRING:
				final int index = typeInfo.indexList.get(paramNum);
				final int stringPosition = resourceData.getInt(
					typeInfo.finalOffset + (index - 1) * TypeSize.RDO.INTEGER);
				final int length = resourceData.getInt(stringPosition);

				byte rawString[] = new byte[length];
				for (int i = 0; i < length; i++)
				{
					rawString[i] = resourceData.get(
						stringPosition + TypeSize.RDO.INTEGER + i);
				}
				stringBuilder.add(new String(rawString, StandardCharsets.UTF_8));
				break;
			default:
				return null;
			}
		}
		return stringBuilder.getString();
	}

  /*――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――/
 /                          PARSING PATTERN ENTRIES                          /
/――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――*/

	private final TraceOutput parsePatternEntry(final Database.Entry entry)
	{
		final ByteBuffer patternHeader = entry.header;

		Tracer.prepareBufferForReading(patternHeader);

		final double time = patternHeader.getDouble();
		Tracer.skipPart(patternHeader, TypeSize.BYTE);
		final TraceType traceType;

		//TODO trace system events when implemented
		switch(patternHeader.get())
		{
		case 0:
			traceType = TraceType.EVENT;
			break;
		case 1:
			traceType = TraceType.RULE;
			break;
		case 2:
			traceType = TraceType.OPERATION_BEGIN;
			break;
		case 3:
			traceType = TraceType.OPERATION_END;
			break;
		default:
			return null;
		}

		return
			new TraceOutput(
				traceType,
				new StringJoin(delimiter)
					.add(traceType.toString())
					.add(checkIntegerValuedReal(time))
					.add(parsePatternData(entry.data, traceType))
					.getString()
			);
	}

	private final String parsePatternData(
		final ByteBuffer patternData,
		final TraceType patternType
	)
	{
		final StringJoin stringBuilder = new StringJoin(delimiter);

		Tracer.prepareBufferForReading(patternData);
		int patternNumber;

		switch(patternType)
		{
		case EVENT:
		{
			patternNumber = patternData.getInt();
			stringBuilder
				.add(String.valueOf(patternNumber + 1))
				.add(String.valueOf(patternNumber + 1));
			break;
		}
		case RULE:
		{
			int dptNumber = patternData.getInt();
			int activityNumber = patternData.getInt();
			patternNumber = decisionPointsInfo.get(dptNumber)
				.activitiesInfo.get(activityNumber).patternNumber;
			stringBuilder
				.add(String.valueOf(1))
				.add(String.valueOf(activityNumber + 1))
				.add(String.valueOf(patternNumber + 1));
			break;
		}
		case OPERATION_BEGIN:
		case OPERATION_END:
		{
			int dptNumber = patternData.getInt();
			int activityNumber = patternData.getInt();
			int actionNumber = patternData.getInt();
			patternNumber = decisionPointsInfo.get(dptNumber)
					.activitiesInfo.get(activityNumber).patternNumber;
			stringBuilder
				.add(String.valueOf(actionNumber + 1))
				.add(String.valueOf(activityNumber + 1))
				.add(String.valueOf(patternNumber + 1));
		}
			break;
		default:
			return null;
		}

		int numberOfRelevantResources = patternData.getInt();
		stringBuilder.add(String.valueOf(numberOfRelevantResources));
		stringBuilder.add("");
		for(int num = 0; num < numberOfRelevantResources; num++)
		{
			final int typeNum =
				patternsInfo.get(patternNumber).relResTypes.get(num);
			final int resNum = patternData.getInt();
			if (legacyResourceIndexes.get(typeNum).get(resNum) == null)
			{
				stringBuilder.add(String.valueOf(getNewId(typeNum, resNum)));
			}
			else
			{
				final int legacyId =
					legacyResourceIndexes.get(typeNum).get(resNum);
				stringBuilder.add(String.valueOf(legacyId));
			}
		}

		return stringBuilder.getString();
	}

  /*――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――/
 /                           PARSING RESULT ENTRIES                          /
/――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――*/

	private final TraceOutput parseResultEntry(final Database.Entry entry)
	{
		final ByteBuffer resultHeader = entry.header;

		Tracer.prepareBufferForReading(resultHeader);

		final double time = resultHeader.getDouble();
		Tracer.skipPart(resultHeader, TypeSize.BYTE);
		final int resultNum = resultHeader.getInt();

		final ModelStructureHelper.ValueType valueType = resultsInfo.get(resultNum).valueType;

		return
			new TraceOutput(
			TraceType.RESULT,
			new StringJoin(delimiter)
				.add(TraceType.RESULT.toString())
				.add(checkIntegerValuedReal(time))
				.add(String.valueOf(resultNum + 1))
				.add(parseResultParameter(entry.data, valueType))
				.getString()
			);
	}

	private final String parseResultParameter(
		final ByteBuffer resultData,
		final ModelStructureHelper.ValueType valueType
	)
	{
		Tracer.prepareBufferForReading(resultData);

		switch(valueType)
		{
		case INTEGER:
			return String.valueOf(resultData.getInt());
		case REAL:
			return checkIntegerValuedReal(resultData.getDouble());
		case BOOLEAN:
			return legacyBooleanString(resultData.get() != 0);
		case ENUM:
			return String.valueOf(resultData.getShort());
		case STRING:
			final ByteArrayOutputStream rawString = new ByteArrayOutputStream();
			while (resultData.hasRemaining())
			{
				rawString.write(resultData.get());
			}
			return rawString.toString();
		default:
			break;
		}

		return null;
	}

  /*――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――/
 /                               HELPER METHODS                              /
/――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――*/

	final private void initializeTypes()
	{
		for (Map.Entry<String, Database.PermanentResourceTypeIndex> type :
			Simulator.getDatabase().permanentResourceIndex.entrySet())
		{
			int typeNum = type.getValue().number;
			legacyResourceIndexes.put(
				typeNum,
				new HashMap<Integer, Integer>()
			);
		}

		for (Map.Entry<String, Database.TemporaryResourceTypeIndex> type :
				Simulator.getDatabase().temporaryResourceIndex.entrySet())
		{
			int typeNum = type.getValue().number;
			legacyResourceIndexes.put(
				typeNum,
				new HashMap<Integer, Integer>()
			);
		}
	}

	private final void freeId(int typeNum, int resNum)
	{
		int legacyId = legacyResourceIndexes.get(typeNum).get(resNum);
		legacyResourceIndexes.get(typeNum).remove(resNum);
		takenIds.remove(legacyId);
	}

	private final int getNewId(int typeNum, int resNum)
	{
		int current;
		int legacyId = 1;
		Iterator<Integer> it = takenIds.iterator();
		while (it.hasNext())
		{
			current = it.next();
			if (current != legacyId)
				break;
			legacyId++;
		}
		legacyResourceIndexes.get(typeNum).put(resNum, legacyId);
		takenIds.add(legacyId);
		return legacyId;
	}

	private final String legacyBooleanString(boolean value)
	{
		return value ? "TRUE" : "FALSE";
	}

	private final String checkIntegerValuedReal(double value)
	{
		return (int) value == value ?
			String.valueOf((int) value) : String.valueOf(value);
	}
}
