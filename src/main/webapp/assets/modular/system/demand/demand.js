layui.use(['layer', 'form', 'table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 系统管理--消息管理
     */
    var Demand = {
        tableId: "demandTable"    //表格id
    };

    /**
     * 初始化表格的列
     */
    Demand.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'demandId', sort: true, title: '单号'},
            {field: 'demandName', sort: true, title: '姓名'},
            {field: 'department', sort: true, title: '部门'},
            {field: 'place', sort: true, title: '地点'},
            {field: 'exception', sort: true, title: '异常问题'},
            {field: 'phenomenon', sort: true, title: '描述'},
            {field: 'engineer', sort: true, title: '工程师'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };



    /**
     * 点击查询按钮
     */
    Demand.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Demand.tableId, {where: queryData});
    };

    /**
     * 弹出添加通知
     */
    Demand.openAddDemand = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加需求',
            content: Feng.ctxPath +'/demand/demand_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Demand.tableId);
            }
        });
    };

    /**
     * 点击编辑通知
     *
     * @param data 点击按钮时候的行数据
     */
    Demand.onEditDemand = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改需求',
            content: Feng.ctxPath + '/demand/demand_update/' + data.demandId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Demand.tableId);
            }
        });
    };

    /**
     * 点击详情
     *
     * @param data 点击按钮时候的行数据
     */
    Demand.onDetailsDemand = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '需求详情',
            content: Feng.ctxPath + '/demand/demand_updateD/' + data.demandId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Demand.tableId);
            }
        });
    };

    /**
     * 点击删除通知
     *
     * @param data 点击按钮时候的行数据
     */
    Demand.onDeleteDemand = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/demand/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Demand.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("demandId", data.demandId);
            ajax.start();
        };
        Feng.confirm("是否删除工单 " + data.demandId + "?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Demand.tableId,
        url: Feng.ctxPath + '/demand/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Demand.initColumn(),
        where: {

        }
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Demand.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Demand.openAddDemand();
    });


    /**
     * 导出excel按钮
     */
    Demand.exportExcel = function () {
        var checkRows = table.checkStatus(Demand.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    // 导出excel
    $('#btnExp').click(function () {
        Demand.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Demand.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Demand.onEditDemand(data);
        } else if (layEvent === 'delete') {
            Demand.onDeleteDemand(data);
        } else if(layEvent === 'details'){
            Demand.onDetailsDemand(data);
        }
    });
});
