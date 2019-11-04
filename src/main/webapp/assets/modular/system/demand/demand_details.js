/**
 * 角色详情对话框
 */
var DemandInfoDlg = {
    data: {
        pid: "",
        place: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化需求的详情数据
    var ajax = new $ax(Feng.ctxPath + "/demand/getDemandInfo?demandId=" + Feng.getUrlParam("demandId"));
    var result = ajax.start();
    form.val('demandForms',result.data);

});