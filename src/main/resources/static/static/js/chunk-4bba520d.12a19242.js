(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4bba520d"],{1834:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:6,xs:24}},[a("dept-card",{attrs:{dept:t.dept}})],1)],1)],1)},i=[],n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-card",{staticClass:"card"},[a("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[a("span",[t._v("科室信息")])]),a("div",{staticClass:"user-profile"},[a("div",{staticClass:"box-center"},[a("pan-thumb-inhoverable",{attrs:{image:t.dept.avatar,height:"100px",width:"100px"}})],1),a("div",{staticClass:"box-center"},[a("div",{staticClass:"user-name text-center"},[t._v(" "+t._s(t.dept.name)+" ")]),a("div",{staticClass:"user-role text-center text-muted"},[t._v(" "+t._s(t.dept.code)+" ")])])]),a("div",{staticClass:"user-bio"},[a("div",{staticClass:"user-education user-bio-section"},[a("div",{staticClass:"user-bio-section-header"},[a("i",{staticClass:"el-icon-location-outline"}),a("span",[t._v("地址")])]),a("div",{staticClass:"user-bio-section-body"},[a("div",{staticClass:"text-muted"},[t._v(" "+t._s(t.dept.location)+" ")])])]),a("div",{staticClass:"user-skills user-bio-section"},[a("div",{staticClass:"user-bio-section-header"},[a("i",{staticClass:"el-icon-tickets"}),a("span",[t._v("备注")])]),a("div",{staticClass:"user-bio-section-body"},[a("div",{staticClass:"text-muted"},[t._v(" "+t._s(t.dept.remark)+" ")])])])])])},r=[],c=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"pan-item",style:{zIndex:t.zIndex,height:t.height,width:t.width}},[a("div",{staticClass:"pan-thumb",style:{backgroundImage:"url("+t.image+")"}})])},d=[],l=(a("a9e3"),{name:"PanThumb",props:{image:{type:String,required:!0},zIndex:{type:Number,default:1},width:{type:String,default:"150px"},height:{type:String,default:"150px"}}}),o=l,u=(a("869e"),a("2877")),p=Object(u["a"])(o,c,d,!1,null,"732b4fda",null),v=p.exports,b={components:{PanThumbInhoverable:v},props:{dept:{type:Object,default:function(){return{code:"",name:"",avatar:"",location:"",remark:""}}}},data:function(){return{}}},h=b,m=(a("6a4c2"),Object(u["a"])(h,n,r,!1,null,"52d9bd3e",null)),f=m.exports,C={components:{DeptCard:f},data:function(){return{dept:{code:"100",name:"眼科",avatar:"http://172.16.100.252:8025/api/user/avatar?userCode=0306",location:"行政楼6楼",remark:"test"}}}},x=C,_=Object(u["a"])(x,s,i,!1,null,null,null);e["default"]=_.exports},"6a4c2":function(t,e,a){"use strict";a("925d")},"84a6":function(t,e,a){},"869e":function(t,e,a){"use strict";a("84a6")},"925d":function(t,e,a){}}]);