webpackJsonp([5],{182:function(t,n,e){"use strict";function r(t){i||e(366)}Object.defineProperty(n,"__esModule",{value:!0});var o=e(265),a=e(367),i=!1,s=e(4),c=r,u=s(o.a,a.a,!1,c,"data-v-5e365a83",null);u.options.__file="src/views/account.vue",n.default=u.exports},265:function(t,n,e){"use strict";var r=e(0),o=e.n(r);n.a={data:function(){return{amount:0,columns:[{title:" ",width:"92px",align:"right",render:function(t,n){return t("router-link",{props:{to:{name:"snap_goods",params:{snapId:n.row.id}}}},[t("img",{domProps:{src:n.row.snapPicUrl},style:{width:"72px"}})])}},{title:"商品信息",width:"192px",render:function(t,n){return t("router-link",{props:{to:{name:"snap_goods",params:{snapId:n.row.id}}}},[t("span",{},n.row.snapGoodsName)])}},{title:"单价",render:function(t,n){return t("span",{props:{type:"text"}},"¥"+n.row.snapPrice)}},{title:"数量",key:"amount"},{title:"小记",render:function(t,n){return t("span",{props:{type:"text"}},"¥"+n.row.snapPrice*n.row.amount)}},{title:"购买时间",width:"192px",render:function(t,n){return t("span",{props:{type:"text"}},o()(String(n.row.purchaseTime)).format("YYYY/MM/DD hh:mm"))}}],recordList:[],loading:!0}},methods:{fetchData:function(){var t=this;this.$http.get("/history").then(function(n){t.recordList=n.data.data,t.amount=t.recordList.reduce(function(t,n){return t+n.snapPrice*n.amount},0),t.loading=!1})}},mounted:function(){console.log("record mounted!"),this.fetchData()}}},366:function(t,n){},367:function(t,n,e){"use strict";var r=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"e-account"},[e("Table",{ref:"cartGoods",attrs:{loading:t.loading,columns:t.columns,data:t.recordList}}),t._v(" "),e("div",{staticStyle:{"text-align":"right"}},[e("span",{staticClass:"amount",staticStyle:{}},[t._v("总计：¥"+t._s(this.amount))])])],1)},o=[];r._withStripped=!0;var a={render:r,staticRenderFns:o};n.a=a}});