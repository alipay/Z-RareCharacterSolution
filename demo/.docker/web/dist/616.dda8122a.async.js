"use strict";(self.webpackChunkrare_words_demo=self.webpackChunkrare_words_demo||[]).push([[616],{58985:function(ut,Te,E){E.d(Te,{Z:function(){return be}});var ye=E(94933),J=`accept acceptCharset accessKey action allowFullScreen allowTransparency
    alt async autoComplete autoFocus autoPlay capture cellPadding cellSpacing challenge
    charSet checked classID className colSpan cols content contentEditable contextMenu
    controls coords crossOrigin data dateTime default defer dir disabled download draggable
    encType form formAction formEncType formMethod formNoValidate formTarget frameBorder
    headers height hidden high href hrefLang htmlFor httpEquiv icon id inputMode integrity
    is keyParams keyType kind label lang list loop low manifest marginHeight marginWidth max maxLength media
    mediaGroup method min minLength multiple muted name noValidate nonce open
    optimum pattern placeholder poster preload radioGroup readOnly rel required
    reversed role rowSpan rows sandbox scope scoped scrolling seamless selected
    shape size sizes span spellCheck src srcDoc srcLang srcSet start step style
    summary tabIndex target title type useMap value width wmode wrap`,ue=`onCopy onCut onPaste onCompositionEnd onCompositionStart onCompositionUpdate onKeyDown
    onKeyPress onKeyUp onFocus onBlur onChange onInput onSubmit onClick onContextMenu onDoubleClick
    onDrag onDragEnd onDragEnter onDragExit onDragLeave onDragOver onDragStart onDrop onMouseDown
    onMouseEnter onMouseLeave onMouseMove onMouseOut onMouseOver onMouseUp onSelect onTouchCancel
    onTouchEnd onTouchMove onTouchStart onScroll onWheel onAbort onCanPlay onCanPlayThrough
    onDurationChange onEmptied onEncrypted onEnded onError onLoadedData onLoadedMetadata
    onLoadStart onPause onPlay onPlaying onProgress onRateChange onSeeked onSeeking onStalled onSuspend onTimeUpdate onVolumeChange onWaiting onLoad onError`,Z="".concat(J," ").concat(ue).split(/[\s\n]+/),z="aria-",De="data-";function t(ee,X){return ee.indexOf(X)===0}function be(ee){var X=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1,K;X===!1?K={aria:!0,data:!0,attr:!0}:X===!0?K={aria:!0}:K=(0,ye.Z)({},X);var se={};return Object.keys(ee).forEach(function(U){(K.aria&&(U==="role"||t(U,z))||K.data&&t(U,De)||K.attr&&Z.includes(U))&&(se[U]=ee[U])}),se}},44611:function(ut,Te,E){E.d(Te,{Z:function(){return Pt}});var ye=E(39262),J=E(94933),ue=E(8151),Z=E(55519),z=E(70767),De=E(85982),t=E(71062),be=E(57160),ee=E(12124),X=E.n(ee),K=E(98722),se=t.forwardRef(function(e,i){var n=e.height,a=e.offsetY,g=e.offsetX,r=e.children,o=e.prefixCls,c=e.onInnerResize,m=e.innerProps,S=e.rtl,v=e.extra,s={},d={display:"flex",flexDirection:"column"};if(a!==void 0){var h;s={height:n,position:"relative",overflow:"hidden"},d=(0,J.Z)((0,J.Z)({},d),{},(h={transform:"translateY(".concat(a,"px)")},(0,z.Z)(h,S?"marginRight":"marginLeft",-g),(0,z.Z)(h,"position","absolute"),(0,z.Z)(h,"left",0),(0,z.Z)(h,"right",0),(0,z.Z)(h,"top",0),h))}return t.createElement("div",{style:s},t.createElement(K.Z,{onResize:function(f){var R=f.offsetHeight;R&&c&&c()}},t.createElement("div",(0,ye.Z)({style:d,className:X()((0,z.Z)({},"".concat(o,"-holder-inner"),o)),ref:i},m),r,v)))});se.displayName="Filler";var U=se,Y=E(17492);function Xe(e,i){var n="touches"in e?e.touches[0]:e;return n[i?"pageX":"pageY"]}var st=t.forwardRef(function(e,i){var n,a=e.prefixCls,g=e.rtl,r=e.scrollOffset,o=e.scrollRange,c=e.onStartMove,m=e.onStopMove,S=e.onScroll,v=e.horizontal,s=e.spinSize,d=e.containerSize,h=t.useState(!1),M=(0,Z.Z)(h,2),f=M[0],R=M[1],L=t.useState(null),b=(0,Z.Z)(L,2),O=b[0],V=b[1],ce=t.useState(null),B=(0,Z.Z)(ce,2),_=B[0],T=B[1],$=!g,w=t.useRef(),te=t.useRef(),y=t.useState(!1),P=(0,Z.Z)(y,2),G=P[0],ne=P[1],fe=t.useRef(),N=function(){clearTimeout(fe.current),ne(!0),fe.current=setTimeout(function(){ne(!1)},3e3)},Q=o-d||0,ve=d-s||0,xe=Q>0,W=t.useMemo(function(){if(r===0||Q===0)return 0;var I=r/Q;return I*ve},[r,Q,ve]),de=function(x){x.stopPropagation(),x.preventDefault()},he=t.useRef({top:W,dragging:f,pageY:O,startTop:_});he.current={top:W,dragging:f,pageY:O,startTop:_};var re=function(x){R(!0),V(Xe(x,v)),T(he.current.top),c(),x.stopPropagation(),x.preventDefault()};t.useEffect(function(){var I=function(me){me.preventDefault()},x=w.current,k=te.current;return x.addEventListener("touchstart",I),k.addEventListener("touchstart",re),function(){x.removeEventListener("touchstart",I),k.removeEventListener("touchstart",re)}},[]);var Ze=t.useRef();Ze.current=Q;var ge=t.useRef();ge.current=ve,t.useEffect(function(){if(f){var I,x=function(me){var Se=he.current,Pe=Se.dragging,He=Se.pageY,ae=Se.startTop;if(Y.Z.cancel(I),Pe){var Le=Xe(me,v)-He,oe=ae;!$&&v?oe-=Le:oe+=Le;var le=Ze.current,we=ge.current,ie=we?oe/we:0,F=Math.ceil(ie*le);F=Math.max(F,0),F=Math.min(F,le),I=(0,Y.Z)(function(){S(F,v)})}},k=function(){R(!1),m()};return window.addEventListener("mousemove",x),window.addEventListener("touchmove",x),window.addEventListener("mouseup",k),window.addEventListener("touchend",k),function(){window.removeEventListener("mousemove",x),window.removeEventListener("touchmove",x),window.removeEventListener("mouseup",k),window.removeEventListener("touchend",k),Y.Z.cancel(I)}}},[f]),t.useEffect(function(){N()},[r]),t.useImperativeHandle(i,function(){return{delayHidden:N}});var A="".concat(a,"-scrollbar"),H={position:"absolute",visibility:G&&xe?null:"hidden"},D={position:"absolute",background:"rgba(0, 0, 0, 0.5)",borderRadius:99,cursor:"pointer",userSelect:"none"};return v?(H.height=8,H.left=0,H.right=0,H.bottom=0,D.height="100%",D.width=s,$?D.left=W:D.right=W):(H.width=8,H.top=0,H.bottom=0,$?H.right=0:H.left=0,D.width="100%",D.height=s,D.top=W),t.createElement("div",{ref:w,className:X()(A,(n={},(0,z.Z)(n,"".concat(A,"-horizontal"),v),(0,z.Z)(n,"".concat(A,"-vertical"),!v),(0,z.Z)(n,"".concat(A,"-visible"),G),n)),style:H,onMouseDown:de,onMouseMove:N},t.createElement("div",{ref:te,className:X()("".concat(A,"-thumb"),(0,z.Z)({},"".concat(A,"-thumb-moving"),f)),style:D,onMouseDown:re}))}),Ve=st;function ct(e){var i=e.children,n=e.setRef,a=t.useCallback(function(g){n(g)},[]);return t.cloneElement(i,{ref:a})}function ft(e,i,n,a,g,r,o){var c=o.getKey;return e.slice(i,n+1).map(function(m,S){var v=i+S,s=r(m,v,{style:{width:a}}),d=c(m);return t.createElement(ct,{key:d,setRef:function(M){return g(m,M)}},s)})}var vt=E(91188),dt=E(56904),ht=E(54460),gt=function(){function e(){(0,dt.Z)(this,e),this.maps=void 0,this.id=0,this.maps=Object.create(null)}return(0,ht.Z)(e,[{key:"set",value:function(n,a){this.maps[n]=a,this.id+=1}},{key:"get",value:function(n){return this.maps[n]}}]),e}(),mt=gt;function St(e,i,n){var a=t.useState(0),g=(0,Z.Z)(a,2),r=g[0],o=g[1],c=(0,t.useRef)(new Map),m=(0,t.useRef)(new mt),S=(0,t.useRef)();function v(){Y.Z.cancel(S.current)}function s(){v(),S.current=(0,Y.Z)(function(){c.current.forEach(function(h,M){if(h&&h.offsetParent){var f=(0,vt.Z)(h),R=f.offsetHeight;m.current.get(M)!==R&&m.current.set(M,f.offsetHeight)}}),o(function(h){return h+1})})}function d(h,M){var f=e(h),R=c.current.get(f);M?(c.current.set(f,M),s()):c.current.delete(f),!R!=!M&&(M?i==null||i(h):n==null||n(h))}return(0,t.useEffect)(function(){return v},[]),[d,s,m.current,r]}function Rt(e,i,n,a,g,r,o,c){var m=t.useRef();return function(S){if(S==null){c();return}if(Y.Z.cancel(m.current),typeof S=="number")o(S);else if(S&&(0,ue.Z)(S)==="object"){var v,s=S.align;"index"in S?v=S.index:v=i.findIndex(function(f){return g(f)===S.key});var d=S.offset,h=d===void 0?0:d,M=function f(R,L){if(!(R<0||!e.current)){var b=e.current.clientHeight,O=!1,V=L;if(b){for(var ce=L||s,B=0,_=0,T=0,$=Math.min(i.length,v),w=0;w<=$;w+=1){var te=g(i[w]);_=B;var y=n.get(te);T=_+(y===void 0?a:y),B=T,w===v&&y===void 0&&(O=!0)}var P=null;switch(ce){case"top":P=_-h;break;case"bottom":P=T-b+h;break;default:{var G=e.current.scrollTop,ne=G+b;_<G?V="top":T>ne&&(V="bottom")}}P!==null&&P!==e.current.scrollTop&&o(P)}m.current=(0,Y.Z)(function(){O&&r(),f(R-1,V)},2)}};M(3)}}}function Gt(e,i,n,a){var g=n-e,r=i-n,o=Math.min(g,r)*2;if(a<=o){var c=Math.floor(a/2);return a%2?n+c+1:n-c}return g>r?n-(a-r):n+(a-g)}function pt(e,i,n){var a=e.length,g=i.length,r,o;if(a===0&&g===0)return null;a<g?(r=e,o=i):(r=i,o=e);var c={__EMPTY_ITEM__:!0};function m(M){return M!==void 0?n(M):c}for(var S=null,v=Math.abs(a-g)!==1,s=0;s<o.length;s+=1){var d=m(r[s]),h=m(o[s]);if(d!==h){S=s,v=v||d!==m(o[s+1]);break}}return S===null?null:{index:S,multiple:v}}function Mt(e,i,n){var a=t.useState(e),g=(0,Z.Z)(a,2),r=g[0],o=g[1],c=t.useState(null),m=(0,Z.Z)(c,2),S=m[0],v=m[1];return t.useEffect(function(){var s=pt(r||[],e||[],i);(s==null?void 0:s.index)!==void 0&&(n==null||n(s.index),v(e[s.index])),o(e)},[e]),[S]}var Et=(typeof navigator=="undefined"?"undefined":(0,ue.Z)(navigator))==="object"&&/Firefox/i.test(navigator.userAgent),ke=Et,Ke=function(e,i){var n=(0,t.useRef)(!1),a=(0,t.useRef)(null);function g(){clearTimeout(a.current),n.current=!0,a.current=setTimeout(function(){n.current=!1},50)}var r=(0,t.useRef)({top:e,bottom:i});return r.current.top=e,r.current.bottom=i,function(o){var c=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1,m=o<0&&r.current.top||o>0&&r.current.bottom;return c&&m?(clearTimeout(a.current),n.current=!1):(!m||n.current)&&g(),!n.current&&m}};function yt(e,i,n,a,g){var r=(0,t.useRef)(0),o=(0,t.useRef)(null),c=(0,t.useRef)(null),m=(0,t.useRef)(!1),S=Ke(i,n);function v(R){Y.Z.cancel(o.current);var L=R.deltaY;r.current+=L,c.current=L,!S(L)&&(ke||R.preventDefault(),o.current=(0,Y.Z)(function(){var b=m.current?10:1;g(r.current*b),r.current=0}))}function s(R){var L=R.deltaX;g(L,!0),ke||R.preventDefault()}var d=(0,t.useRef)(null),h=(0,t.useRef)(null);function M(R){if(e){Y.Z.cancel(h.current),h.current=(0,Y.Z)(function(){d.current=null},2);var L=R.deltaX,b=R.deltaY,O=Math.abs(L),V=Math.abs(b);d.current===null&&(d.current=a&&O>V?"x":"y"),d.current==="x"?s(R):v(R)}}function f(R){e&&(m.current=R.detail===c.current)}return[M,f]}var ze=E(99657),bt=14/15;function xt(e,i,n){var a=(0,t.useRef)(!1),g=(0,t.useRef)(0),r=(0,t.useRef)(null),o=(0,t.useRef)(null),c,m=function(d){if(a.current){var h=Math.ceil(d.touches[0].pageY),M=g.current-h;g.current=h,n(M)&&d.preventDefault(),clearInterval(o.current),o.current=setInterval(function(){M*=bt,(!n(M,!0)||Math.abs(M)<=.1)&&clearInterval(o.current)},16)}},S=function(){a.current=!1,c()},v=function(d){c(),d.touches.length===1&&!a.current&&(a.current=!0,g.current=Math.ceil(d.touches[0].pageY),r.current=d.target,r.current.addEventListener("touchmove",m),r.current.addEventListener("touchend",S))};c=function(){r.current&&(r.current.removeEventListener("touchmove",m),r.current.removeEventListener("touchend",S))},(0,ze.Z)(function(){return e&&i.current.addEventListener("touchstart",v),function(){var s;(s=i.current)===null||s===void 0||s.removeEventListener("touchstart",v),c(),clearInterval(o.current)}},[e])}var Zt=20;function Be(){var e=arguments.length>0&&arguments[0]!==void 0?arguments[0]:0,i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:0,n=e/i*100;return isNaN(n)&&(n=0),n=Math.max(n,Zt),n=Math.min(n,e/2),Math.floor(n)}var _e=E(72296);function Ct(e,i,n,a){var g=t.useMemo(function(){return[new Map,[]]},[e,n.id,a]),r=(0,Z.Z)(g,2),o=r[0],c=r[1],m=function(v){var s=arguments.length>1&&arguments[1]!==void 0?arguments[1]:v,d=o.get(v),h=o.get(s);if(d===void 0||h===void 0)for(var M=e.length,f=c.length;f<M;f+=1){var R,L=e[f],b=i(L);o.set(b,f);var O=(R=n.get(b))!==null&&R!==void 0?R:a;if(c[f]=(c[f-1]||0)+O,b===v&&(d=f),b===s&&(h=f),d!==void 0&&h!==void 0)break}return{top:c[d-1]||0,bottom:c[h]}};return m}var Lt=["prefixCls","className","height","itemHeight","fullHeight","style","data","children","itemKey","virtual","direction","scrollWidth","component","onScroll","onVirtualScroll","onVisibleChange","innerProps","extraRender"],wt=[],Tt={overflowY:"auto",overflowAnchor:"none"};function Dt(e,i){var n=e.prefixCls,a=n===void 0?"rc-virtual-list":n,g=e.className,r=e.height,o=e.itemHeight,c=e.fullHeight,m=c===void 0?!0:c,S=e.style,v=e.data,s=e.children,d=e.itemKey,h=e.virtual,M=e.direction,f=e.scrollWidth,R=e.component,L=R===void 0?"div":R,b=e.onScroll,O=e.onVirtualScroll,V=e.onVisibleChange,ce=e.innerProps,B=e.extraRender,_=(0,De.Z)(e,Lt),T=!!(h!==!1&&r&&o),$=T&&v&&o*v.length>r,w=M==="rtl",te=X()(a,(0,z.Z)({},"".concat(a,"-rtl"),w),g),y=v||wt,P=(0,t.useRef)(),G=(0,t.useRef)(),ne=(0,t.useState)(0),fe=(0,Z.Z)(ne,2),N=fe[0],Q=fe[1],ve=(0,t.useState)(0),xe=(0,Z.Z)(ve,2),W=xe[0],de=xe[1],he=(0,t.useState)(!1),re=(0,Z.Z)(he,2),Ze=re[0],ge=re[1],A=function(){ge(!0)},H=function(){ge(!1)},D=t.useCallback(function(l){return typeof d=="function"?d(l):l==null?void 0:l[d]},[d]),I={getKey:D};function x(l){Q(function(u){var p;typeof l=="function"?p=l(u):p=l;var C=Yt(p);return P.current.scrollTop=C,C})}var k=(0,t.useRef)({start:0,end:y.length}),Ce=(0,t.useRef)(),me=Mt(y,D),Se=(0,Z.Z)(me,1),Pe=Se[0];Ce.current=Pe;var He=St(D,null,null),ae=(0,Z.Z)(He,4),Le=ae[0],oe=ae[1],le=ae[2],we=ae[3],ie=t.useMemo(function(){if(!T)return{scrollHeight:void 0,start:0,end:y.length-1,offset:void 0};if(!$){var l;return{scrollHeight:((l=G.current)===null||l===void 0?void 0:l.offsetHeight)||0,start:0,end:y.length-1,offset:void 0}}for(var u=0,p,C,j,_t=y.length,Ee=0;Ee<_t;Ee+=1){var At=y[Ee],Ut=D(At),it=le.get(Ut),We=u+(it===void 0?o:it);We>=N&&p===void 0&&(p=Ee,C=u),We>N+r&&j===void 0&&(j=Ee),u=We}return p===void 0&&(p=0,C=0,j=Math.ceil(r/o)),j===void 0&&(j=y.length-1),j=Math.min(j+1,y.length-1),{scrollHeight:u,start:p,end:j,offset:C}},[$,T,N,y,we,r]),F=ie.scrollHeight,Re=ie.start,pe=ie.end,Ue=ie.offset;k.current.start=Re,k.current.end=pe;var Ht=t.useState({width:0,height:r}),Ge=(0,Z.Z)(Ht,2),q=Ge[0],It=Ge[1],Ot=function(u){It(u)},je=(0,t.useRef)(),Je=(0,t.useRef)(),Nt=t.useMemo(function(){return Be(q.width,f)},[q.width,f]),Ft=t.useMemo(function(){return Be(q.height,F)},[q.height,F]),Ie=F-r,Oe=(0,t.useRef)(Ie);Oe.current=Ie;function Yt(l){var u=l;return Number.isNaN(Oe.current)||(u=Math.min(u,Oe.current)),u=Math.max(u,0),u}var Qe=N<=0,qe=N>=Ie,$t=Ke(Qe,qe),Ne=function(){return{x:w?-W:W,y:N}},Fe=(0,t.useRef)(Ne()),Ye=(0,_e.zX)(function(){if(O){var l=Ne();(Fe.current.x!==l.x||Fe.current.y!==l.y)&&(O(l),Fe.current=l)}});function et(l,u){var p=l;u?((0,be.flushSync)(function(){de(p)}),Ye()):x(p)}function Wt(l){var u=l.currentTarget.scrollTop;u!==N&&x(u),b==null||b(l),Ye()}var tt=function(u){var p=u,C=f-q.width;return p=Math.max(p,0),p=Math.min(p,C),p},Xt=(0,_e.zX)(function(l,u){u?((0,be.flushSync)(function(){de(function(p){var C=p+(w?-l:l);return tt(C)})}),Ye()):x(function(p){var C=p+l;return C})}),Vt=yt(T,Qe,qe,!!f,Xt),nt=(0,Z.Z)(Vt,2),$e=nt[0],rt=nt[1];xt(T,P,function(l,u){return $t(l,u)?!1:($e({preventDefault:function(){},deltaY:l}),!0)}),(0,ze.Z)(function(){function l(p){T&&p.preventDefault()}var u=P.current;return u.addEventListener("wheel",$e),u.addEventListener("DOMMouseScroll",rt),u.addEventListener("MozMousePixelScroll",l),function(){u.removeEventListener("wheel",$e),u.removeEventListener("DOMMouseScroll",rt),u.removeEventListener("MozMousePixelScroll",l)}},[T]);var at=function(){var u,p;(u=je.current)===null||u===void 0||u.delayHidden(),(p=Je.current)===null||p===void 0||p.delayHidden()},ot=Rt(P,y,le,o,D,oe,x,at);t.useImperativeHandle(i,function(){return{getScrollInfo:Ne,scrollTo:function(u){function p(C){return C&&(0,ue.Z)(C)==="object"&&("left"in C||"top"in C)}p(u)?(u.left!==void 0&&de(tt(u.left)),ot(u.top)):ot(u)}}}),(0,ze.Z)(function(){if(V){var l=y.slice(Re,pe+1);V(l,y)}},[Re,pe,y]);var kt=Ct(y,D,le,o),Kt=B==null?void 0:B({start:Re,end:pe,virtual:$,offsetX:W,offsetY:Ue,rtl:w,getSize:kt}),Bt=ft(y,Re,pe,f,Le,s,I),Me=null;r&&(Me=(0,J.Z)((0,z.Z)({},m?"height":"maxHeight",r),Tt),T&&(Me.overflowY="hidden",f&&(Me.overflowX="hidden"),Ze&&(Me.pointerEvents="none")));var lt={};return w&&(lt.dir="rtl"),t.createElement("div",(0,ye.Z)({style:(0,J.Z)((0,J.Z)({},S),{},{position:"relative"}),className:te},lt,_),t.createElement(K.Z,{onResize:Ot},t.createElement(L,{className:"".concat(a,"-holder"),style:Me,ref:P,onScroll:Wt,onMouseEnter:at},t.createElement(U,{prefixCls:a,height:F,offsetX:W,offsetY:Ue,scrollWidth:f,onInnerResize:oe,ref:G,innerProps:ce,rtl:w,extra:Kt},Bt))),$&&F>r&&t.createElement(Ve,{ref:je,prefixCls:a,scrollOffset:N,scrollRange:F,rtl:w,onScroll:et,onStartMove:A,onStopMove:H,spinSize:Ft,containerSize:q.height}),$&&f&&t.createElement(Ve,{ref:Je,prefixCls:a,scrollOffset:W,scrollRange:f,rtl:w,onScroll:et,onStartMove:A,onStopMove:H,spinSize:Nt,containerSize:q.width,horizontal:!0}))}var Ae=t.forwardRef(Dt);Ae.displayName="List";var zt=Ae,Pt=zt}}]);
