(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3712269a"],{a93a:function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"container"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:8,xs:24}},[a("log-data-table",{ref:"dataTable",attrs:{names:t.allPoolNames}})],1),a("el-col",{attrs:{span:8,xs:24}},[a("pool-data-table",{ref:"dataTable"})],1),a("el-col",{attrs:{span:8,xs:24}},[a("member-data-table",{ref:"dataTable",attrs:{title:t.devPoolTitle,names:t.devPoolNames}}),a("el-divider"),a("member-data-table",{ref:"dataTable",attrs:{title:t.allPoolTitle,names:t.allPoolNames}})],1)],1)],1)},n=[],r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-card",{staticStyle:{"border-radius":"10px"}},[a("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[a("span",[t._v("抽奖日志")])]),a("el-table",{ref:"logTable",attrs:{data:t.tableData,height:"700px","row-class-name":t.tableRowClassName}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),t._e(),a("el-table-column",{attrs:{prop:"name",label:"姓名",width:"100"}}),a("el-table-column",{attrs:{prop:"feature",label:"奖品"}}),a("el-table-column",{attrs:{label:"操作",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(a){return t.cancel(e.row)}}},[t._v(" 退还奖品 ")])]}}])})],1),a("div",{staticStyle:{"margin-top":"10px","text-align":"center"}},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:14,xs:24}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择人员"},model:{value:t.name,callback:function(e){t.name=e},expression:"name"}},t._l(t.options,(function(t){return a("el-option",{key:t.label,attrs:{label:t.label,value:t.label}})})),1)],1),a("el-col",{attrs:{span:4,xs:24}},[a("el-button",{attrs:{type:"success"},on:{click:t.raffleOnce}},[t._v("单抽")])],1),a("el-col",{attrs:{span:6,xs:24}},[a("el-button",{attrs:{type:"success",loading:t.loading},on:{click:t.cancelAll}},[t._v(" 归还所有奖品 ")])],1)],1)],1)],1)},o=[],s=(a("d81d"),a("b0c0"),a("2b0e")),i=new s["default"],c=a("b775");function u(){return Object(c["a"])({url:"/api/tool/raffle/queryRaffleLogs",method:"get",baseURL:"http://172.16.100.252"})}function d(){return Object(c["a"])({url:"/api/tool/raffle/queryFeaturePool",method:"get",baseURL:"http://172.16.100.252"})}function f(t){return Object(c["a"])({url:"/api/tool/raffle/cancel",method:"post",baseURL:"http://172.16.100.252",data:{key:t}})}function b(){return Object(c["a"])({url:"/api/tool/raffle/cancelAll",method:"post",baseURL:"http://172.16.100.252"})}function p(t,e){return Object(c["a"])({url:"/api/tool/raffle/addFeature",method:"post",baseURL:"http://172.16.100.252",data:{feature:t,count:e}})}function h(t){return Object(c["a"])({url:"/api/tool/raffle/raffleOnce",method:"post",baseURL:"http://172.16.100.252",data:{name:t}})}function m(t){return Object(c["a"])({url:"/api/tool/raffle/raffleList",method:"post",baseURL:"http://172.16.100.252",data:{names:t}})}var g={props:{names:{type:Array,default:function(t){return[]}}},data:function(){return{tableData:[],loading:!1,name:"",options:[]}},mounted:function(){var t=this;this.options=this.names.map((function(t){return{label:t}})),i.$on("refresh",(function(e){u().then((function(e){var a=e.data;t.tableData=a})).then((function(e){var a=t.$refs.logTable.bodyWrapper;a.scrollTop=a.scrollHeight}))}))},methods:{tableRowClassName:function(t){t.row;var e=t.rowIndex;if(e%2===0)return"success-row"},raffleOnce:function(){var t=this;this.loading=!0,h(this.name).then((function(e){i.$emit("refresh",null),t.loading=!1})).catch((function(e){t.loading=!1}))},cancel:function(t){var e=this;this.loading=!0,f(t.key).then((function(t){i.$emit("refresh",null),e.loading=!1}))},cancelAll:function(){var t=this;this.loading=!0,b().then((function(e){i.$emit("refresh",null),t.loading=!1}))}}},v=g,x=a("2877"),w=Object(x["a"])(v,r,o,!1,null,null,null),y=w.exports,_=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-card",{staticStyle:{"border-radius":"10px"}},[a("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[a("span",[t._v("奖品池 (剩余 "+t._s(t.remain)+" 个)")])]),a("el-table",{attrs:{data:t.tableData,height:"700px","row-class-name":t.tableRowClassName}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),a("el-table-column",{attrs:{prop:"feature",label:"奖品"}}),a("el-table-column",{attrs:{prop:"count",label:"数量"}})],1),a("div",{staticStyle:{"margin-top":"10px","text-align":"center"}},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12,xs:24}},[a("el-input",{attrs:{placeholder:"请输入奖品","suffix-icon":"el-icon-edit",clearable:"true"},model:{value:t.feature,callback:function(e){t.feature=e},expression:"feature"}})],1),a("el-col",{attrs:{span:7,xs:24}},[a("el-input",{attrs:{placeholder:"请输入数量"},model:{value:t.count,callback:function(e){t.count=e},expression:"count"}},[a("template",{slot:"prepend"},[t._v("数量")])],2)],1),a("el-col",{attrs:{span:5,xs:24}},[a("el-button",{attrs:{type:"success",loading:t.loading},on:{click:t.addFeature}},[t._v(" 添加奖品 ")])],1)],1)],1)],1)},k=[],O=(a("d3b7"),a("159b"),{data:function(){return{tableData:[],feature:"",count:1,loading:!1,remain:0}},mounted:function(){var t=this;i.$on("refresh",(function(e){d().then((function(e){var a=e.data;t.tableData=a,t.remain=0,t.tableData.forEach((function(e){t.remain+=e.count}))}))}))},methods:{tableRowClassName:function(t){t.row;var e=t.rowIndex;if(e%2===0)return"success-row"},addFeature:function(){var t=this;this.loading=!0,p(this.feature,this.count).then((function(e){i.$emit("refresh",null),t.loading=!1})).catch((function(e){t.loading=!1}))}}}),R=O,T=Object(x["a"])(R,_,k,!1,null,null,null),D=T.exports,$=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-card",{staticStyle:{"border-radius":"10px"}},[a("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[a("span",[t._v(t._s(t.title))])]),a("el-table",{attrs:{data:t.tableData,height:"253px","row-class-name":t.tableRowClassName}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),a("el-table-column",{attrs:{prop:"name",label:"成员"}})],1),a("div",{staticStyle:{"margin-top":"10px","text-align":"center"}},[a("el-button",{attrs:{type:"success",loading:t.loading},on:{click:t.raffleList}},[t._v(" 批量抽奖 ")])],1)],1)},L=[],j={props:{title:{type:String,default:""},names:{type:Array,default:function(t){return[]}}},data:function(){return{tableData:[],loading:!1}},mounted:function(){this.tableData=this.names.map((function(t){return{name:t}}))},methods:{tableRowClassName:function(t){t.row;var e=t.rowIndex;if(e%2===0)return"success-row"},raffleList:function(){var t=this;this.loading=!0,m(this.names).then((function(e){i.$emit("refresh",null),t.loading=!1})).catch((function(e){t.loading=!1}))}}},N=j,P=Object(x["a"])(N,$,L,!1,null,null,null),C=P.exports,S={components:{LogDataTable:y,PoolDataTable:D,MemberDataTable:C},data:function(){return{devPoolTitle:"开发人员",devPoolNames:["覃鹏展","赵庆中","汪富康","庄屹东"],allPoolTitle:"科室成员",allPoolNames:["刘晓东","刘洋","朱旭玮","张智慧","潘学杰","潘长虹","陈宁","陈碧波","贺春","田丽丽","闫志飞","杨海波","曾方泽","黎伟","巩昕昊","王伟","程泉","郑超","赵庆中","陈卓","庄屹东","戢凯","覃鹏展","王亮","牛乐","刘南鋆","潘峥","唐富明","汪富康","刘天韵","刘杨文昊","魏巍"]}},mounted:function(){i.$emit("refresh",null)}},U=S,A=(a("dbc3"),Object(x["a"])(U,l,n,!1,null,"77b0c3c6",null));e["default"]=A.exports},bbca:function(t,e,a){},dbc3:function(t,e,a){"use strict";a("bbca")}}]);