/*! highlight.js v9.7.0 | BSD3 License | git.io/hljslicense */
!function(e){var n="object"==typeof window&&window||"object"==typeof self&&self;"undefined"!=typeof exports?e(exports):n&&(n.hljs=e({}),"function"==typeof define&&define.amd&&define([],function(){return n.hljs}))}(function(e){function n(e){return e.replace(/[&<>]/gm,function(e){return A[e]})}function r(e){return e.nodeName.toLowerCase()}function t(e,n){var r=e&&e.exec(n);return r&&0===r.index}function a(e){return L.test(e)}function c(e){var n,r,t,c,i=e.className+" ";if(i+=e.parentNode?e.parentNode.className:"",r=y.exec(i))return E(r[1])?r[1]:"no-highlight";for(i=i.split(/\s+/),n=0,t=i.length;t>n;n++)if(c=i[n],a(c)||E(c))return c}function i(e,n){var r,t={};for(r in e)t[r]=e[r];if(n)for(r in n)t[r]=n[r];return t}function o(e){var n=[];return function t(e,a){for(var c=e.firstChild;c;c=c.nextSibling)3===c.nodeType?a+=c.nodeValue.length:1===c.nodeType&&(n.push({event:"start",offset:a,node:c}),a=t(c,a),r(c).match(/br|hr|img|input/)||n.push({event:"stop",offset:a,node:c}));return a}(e,0),n}function s(e,t,a){function c(){return e.length&&t.length?e[0].offset!==t[0].offset?e[0].offset<t[0].offset?e:t:"start"===t[0].event?e:t:e.length?e:t}function i(e){function t(e){return" "+e.nodeName+'="'+n(e.value)+'"'}l+="<"+r(e)+R.map.call(e.attributes,t).join("")+">"}function o(e){l+="</"+r(e)+">"}function s(e){("start"===e.event?i:o)(e.node)}for(var u=0,l="",f=[];e.length||t.length;){var g=c();if(l+=n(a.substr(u,g[0].offset-u)),u=g[0].offset,g===e){f.reverse().forEach(o);do s(g.splice(0,1)[0]),g=c();while(g===e&&g.length&&g[0].offset===u);f.reverse().forEach(i)}else"start"===g[0].event?f.push(g[0].node):f.pop(),s(g.splice(0,1)[0])}return l+n(a.substr(u))}function u(e){function n(e){return e&&e.source||e}function r(r,t){return new RegExp(n(r),"m"+(e.cI?"i":"")+(t?"g":""))}function t(a,c){if(!a.compiled){if(a.compiled=!0,a.k=a.k||a.bK,a.k){var o={},s=function(n,r){e.cI&&(r=r.toLowerCase()),r.split(" ").forEach(function(e){var r=e.split("|");o[r[0]]=[n,r[1]?Number(r[1]):1]})};"string"==typeof a.k?s("keyword",a.k):w(a.k).forEach(function(e){s(e,a.k[e])}),a.k=o}a.lR=r(a.l||/\w+/,!0),c&&(a.bK&&(a.b="\\b("+a.bK.split(" ").join("|")+")\\b"),a.b||(a.b=/\B|\b/),a.bR=r(a.b),a.e||a.eW||(a.e=/\B|\b/),a.e&&(a.eR=r(a.e)),a.tE=n(a.e)||"",a.eW&&c.tE&&(a.tE+=(a.e?"|":"")+c.tE)),a.i&&(a.iR=r(a.i)),null==a.r&&(a.r=1),a.c||(a.c=[]);var u=[];a.c.forEach(function(e){e.v?e.v.forEach(function(n){u.push(i(e,n))}):u.push("self"===e?a:e)}),a.c=u,a.c.forEach(function(e){t(e,a)}),a.starts&&t(a.starts,c);var l=a.c.map(function(e){return e.bK?"\\.?("+e.b+")\\.?":e.b}).concat([a.tE,a.i]).map(n).filter(Boolean);a.t=l.length?r(l.join("|"),!0):{exec:function(){return null}}}}t(e)}function l(e,r,a,c){function i(e,n){var r,a;for(r=0,a=n.c.length;a>r;r++)if(t(n.c[r].bR,e))return n.c[r]}function o(e,n){if(t(e.eR,n)){for(;e.endsParent&&e.parent;)e=e.parent;return e}return e.eW?o(e.parent,n):void 0}function s(e,n){return!a&&t(n.iR,e)}function g(e,n){var r=m.cI?n[0].toLowerCase():n[0];return e.k.hasOwnProperty(r)&&e.k[r]}function b(e,n,r,t){var a=t?"":k.classPrefix,c='<span class="'+a,i=r?"":M;return c+=e+'">',c+n+i}function d(){var e,r,t,a;if(!w.k)return n(y);for(a="",r=0,w.lR.lastIndex=0,t=w.lR.exec(y);t;)a+=n(y.substr(r,t.index-r)),e=g(w,t),e?(B+=e[1],a+=b(e[0],n(t[0]))):a+=n(t[0]),r=w.lR.lastIndex,t=w.lR.exec(y);return a+n(y.substr(r))}function p(){var e="string"==typeof w.sL;if(e&&!x[w.sL])return n(y);var r=e?l(w.sL,y,!0,C[w.sL]):f(y,w.sL.length?w.sL:void 0);return w.r>0&&(B+=r.r),e&&(C[w.sL]=r.top),b(r.language,r.value,!1,!0)}function h(){L+=null!=w.sL?p():d(),y=""}function N(e){L+=e.cN?b(e.cN,"",!0):"",w=Object.create(e,{parent:{value:w}})}function v(e,n){if(y+=e,null==n)return h(),0;var r=i(n,w);if(r)return r.skip?y+=n:(r.eB&&(y+=n),h(),r.rB||r.eB||(y=n)),N(r,n),r.rB?0:n.length;var t=o(w,n);if(t){var a=w;a.skip?y+=n:(a.rE||a.eE||(y+=n),h(),a.eE&&(y=n));do w.cN&&(L+=M),w.skip||(B+=w.r),w=w.parent;while(w!==t.parent);return t.starts&&N(t.starts,""),a.rE?0:n.length}if(s(n,w))throw new Error('Illegal lexeme "'+n+'" for mode "'+(w.cN||"<unnamed>")+'"');return y+=n,n.length||1}var m=E(e);if(!m)throw new Error('Unknown language: "'+e+'"');u(m);var R,w=c||m,C={},L="";for(R=w;R!==m;R=R.parent)R.cN&&(L=b(R.cN,"",!0)+L);var y="",B=0;try{for(var A,S,T=0;;){if(w.t.lastIndex=T,A=w.t.exec(r),!A)break;S=v(r.substr(T,A.index-T),A[0]),T=A.index+S}for(v(r.substr(T)),R=w;R.parent;R=R.parent)R.cN&&(L+=M);return{r:B,value:L,language:e,top:w}}catch($){if($.message&&-1!==$.message.indexOf("Illegal"))return{r:0,value:n(r)};throw $}}function f(e,r){r=r||k.languages||w(x);var t={r:0,value:n(e)},a=t;return r.filter(E).forEach(function(n){var r=l(n,e,!1);r.language=n,r.r>a.r&&(a=r),r.r>t.r&&(a=t,t=r)}),a.language&&(t.second_best=a),t}function g(e){return k.tabReplace||k.useBR?e.replace(B,function(e,n){return k.useBR&&"\n"===e?"<br>":k.tabReplace?n.replace(/\t/g,k.tabReplace):void 0}):e}function b(e,n,r){var t=n?C[n]:r,a=[e.trim()];return e.match(/\bhljs\b/)||a.push("hljs"),-1===e.indexOf(t)&&a.push(t),a.join(" ").trim()}function d(e){var n,r,t,i,u,d=c(e);a(d)||(k.useBR?(n=document.createElementNS("http://www.w3.org/1999/xhtml","div"),n.innerHTML=e.innerHTML.replace(/\n/g,"").replace(/<br[ \/]*>/g,"\n")):n=e,u=n.textContent,t=d?l(d,u,!0):f(u),r=o(n),r.length&&(i=document.createElementNS("http://www.w3.org/1999/xhtml","div"),i.innerHTML=t.value,t.value=s(r,o(i),u)),t.value=g(t.value),e.innerHTML=t.value,e.className=b(e.className,d,t.language),e.result={language:t.language,re:t.r},t.second_best&&(e.second_best={language:t.second_best.language,re:t.second_best.r}))}function p(e){k=i(k,e)}function h(){if(!h.called){h.called=!0;var e=document.querySelectorAll("pre code");R.forEach.call(e,d)}}function N(){addEventListener("DOMContentLoaded",h,!1),addEventListener("load",h,!1)}function v(n,r){var t=x[n]=r(e);t.aliases&&t.aliases.forEach(function(e){C[e]=n})}function m(){return w(x)}function E(e){return e=(e||"").toLowerCase(),x[e]||x[C[e]]}var R=[],w=Object.keys,x={},C={},L=/^(no-?highlight|plain|text)$/i,y=/\blang(?:uage)?-([\w-]+)\b/i,B=/((^(<[^>]+>|\t|)+|(?:\n)))/gm,M="</span>",k={classPrefix:"hljs-",tabReplace:null,useBR:!1,languages:void 0},A={"&":"&amp;","<":"&lt;",">":"&gt;"};return e.highlight=l,e.highlightAuto=f,e.fixMarkup=g,e.highlightBlock=d,e.configure=p,e.initHighlighting=h,e.initHighlightingOnLoad=N,e.registerLanguage=v,e.listLanguages=m,e.getLanguage=E,e.inherit=i,e.IR="[a-zA-Z]\\w*",e.UIR="[a-zA-Z_]\\w*",e.NR="\\b\\d+(\\.\\d+)?",e.CNR="(-?)(\\b0[xX][a-fA-F0-9]+|(\\b\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?)",e.BNR="\\b(0b[01]+)",e.RSR="!|!=|!==|%|%=|&|&&|&=|\\*|\\*=|\\+|\\+=|,|-|-=|/=|/|:|;|<<|<<=|<=|<|===|==|=|>>>=|>>=|>=|>>>|>>|>|\\?|\\[|\\{|\\(|\\^|\\^=|\\||\\|=|\\|\\||~",e.BE={b:"\\\\[\\s\\S]",r:0},e.ASM={cN:"string",b:"'",e:"'",i:"\\n",c:[e.BE]},e.QSM={cN:"string",b:'"',e:'"',i:"\\n",c:[e.BE]},e.PWM={b:/\b(a|an|the|are|I'm|isn't|don't|doesn't|won't|but|just|should|pretty|simply|enough|gonna|going|wtf|so|such|will|you|your|like)\b/},e.C=function(n,r,t){var a=e.inherit({cN:"comment",b:n,e:r,c:[]},t||{});return a.c.push(e.PWM),a.c.push({cN:"doctag",b:"(?:TODO|FIXME|NOTE|BUG|XXX):",r:0}),a},e.CLCM=e.C("//","$"),e.CBCM=e.C("/\\*","\\*/"),e.HCM=e.C("#","$"),e.NM={cN:"number",b:e.NR,r:0},e.CNM={cN:"number",b:e.CNR,r:0},e.BNM={cN:"number",b:e.BNR,r:0},e.CSSNM={cN:"number",b:e.NR+"(%|em|ex|ch|rem|vw|vh|vmin|vmax|cm|mm|in|pt|pc|px|deg|grad|rad|turn|s|ms|Hz|kHz|dpi|dpcm|dppx)?",r:0},e.RM={cN:"regexp",b:/\//,e:/\/[gimuy]*/,i:/\n/,c:[e.BE,{b:/\[/,e:/\]/,r:0,c:[e.BE]}]},e.TM={cN:"title",b:e.IR,r:0},e.UTM={cN:"title",b:e.UIR,r:0},e.METHOD_GUARD={b:"\\.\\s*"+e.UIR,r:0},e.registerLanguage("rao",function(e){return{cI:!0,l:/[a-zA-Z_$][a-zA-Z0-9_]*/,k:{keyword:"type new operation rule event relevant relevants combination of types logic edge set activity search sequence constant result resultType var val if else for switch case default enum int long double boolean as Value String array break return resource frame background drawText drawRectangle drawLine drawCircle drawEllipse drawTriangle RaoColor BLACK BLUE CYAN DARK_BLUE DARK_CYAN DARK_GRAY DARK_GREEN DARK_MAGENTA DARK_RED DARK_YELLOW GRAY GREEN MAGENTA RED WHITE YELLOW Alignment LEFT CENTER RIGHT",constant:"true false",built_in:"accessible all filter any onlyif exists forall next minBySafe maxBySafe condition parent priority startCondition heuristic compareTops terminateCondition init draw DiscreteHistogram ContinuousHistogram Values Uniform Exponential Normal Triangular duration begin end execute stop plan create erase currentTime"},c:[e.CLCM,e.CBCM,e.QSM,e.ASM,e.CNM,{cN:"trace-resource-create",b:"^RC ",e:"$"},{cN:"trace-resource-keep",b:"^RK ",e:"$"},{cN:"trace-resource-erase",b:"^RE ",e:"$"},{cN:"trace-system",b:"^ES ",e:"$"},{cN:"trace-operation-begin",b:"^EB ",e:"$"},{cN:"trace-operation-end",b:"^EF ",e:"$"},{cN:"trace-event",b:"^EI ",e:"$"},{cN:"trace-rule",b:"^ER ",e:"$"},{cN:"trace-result",b:"^V ",e:"$"},{cN:"trace-search-begin",b:"^SB ",e:"$"},{cN:"trace-search-open",b:"^SO ",e:"$"},{cN:"trace-search-spawn-new",b:"^STN ",e:"$"},{cN:"trace-search-spawn-worse",b:"^STD ",e:"$"},{cN:"trace-search-spawn-better",b:"^STR ",e:"$"},{cN:"trace-search-resource-keep",b:"^SRK ",e:"$"},{cN:"trace-search-decision",b:"^SD ",e:"$"},{cN:"trace-search-end-aborted",b:"^SEA ",e:"$"},{cN:"trace-search-end-condition",b:"^SEC ",e:"$"},{cN:"trace-search-end-success",b:"^SES ",e:"$"},{cN:"trace-search-end-fail",b:"^SEN ",e:"$"},{cN:"trace-process",b:"^PR ",e:"$"}]}}),e});