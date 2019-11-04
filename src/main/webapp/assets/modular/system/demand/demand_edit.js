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
    form.val('demandForm',result.data);

    // 点击上级角色时
    $('#place').click(function () {
        var formName = encodeURIComponent("parent.DemandInfoDlg.data.place");
        var formId = encodeURIComponent("parent.DemandInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/demand/demandTreeList");

        layer.open({
            type: 2,
            title: '父级角色选择',
            area: ['300px', '200px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#pid").val(DemandInfoDlg.data.pid);
                $("#place").val(DemandInfoDlg.data.place);
            }
        });
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/demand/edit", function (data) {
            Feng.success("操作成功!");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
    });
});