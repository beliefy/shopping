//安全退出
function admin_logout() {
    var r = confirm("确认要退出吗?");
    if (r) {
        window.location = "/doctorLogin/logout";
    }
}

function addDoctor()
{
    var username = $("#addUsername").val();
    var name = $("#addName").val();
    var department = $("#addDepartment").val();
    var sex = $("#addSex").val();
    if(username==""||name==""||department==""||sex=="")
    {
        alert("数据不能为空！")
    }
    else {
        $.post("/doctor/add", {
            username:username,
            name:name,
            department:department,
            sex:sex
        }, function (data) {
            if(data=="1"){
                alert("该工号已被占用！");
            }
            else {
                window.location = "/admin/doctor";
            }
        });
    }
}

function addMedicine() {
    var name = $("#addName").val();
    var price = $("#addPrice").val();
    var num = $("#addNum").val();
    if(name==""||price==""||num==""){
        alert("数据不能为空！")
    }
    else{
        $.post("/medicine/add",{
            name:name,
            price:price,
            num:num
        },function (data) {
            if(data=="1"){
                alert("该药品已经存在！");
            }
            else{
                window.location = "/admin/medicine";
            }
        });
    }
}

function addCase() {
    var doctorId = $("#doctorId").val();
    var date = $("#addDate").val();
    var patient_name = $("#addPatientName").val();
    var ill = $("#addIll").val();
    var remark = $("#addRemark").val();
    if(date==""||patient_name==""||ill==""){
        alert("数据不能为空！")
    }
    else{
        $.post("/case/add",{
            doctor_id:doctorId,
            date:date,
            patient_name:patient_name,
            ill:ill,
            remark:remark
        },function (data) {
            if(data=="1"){
                alert("该患者不存在！");
            }
            else{
                window.location = "/doctor/case";
            }
        });
    }
}

function editDoctor()
{
    var id = $("#id").val();
    var name = $("#name").val();
    var department = $("#department").val();
    var username = $("#username").val();
    var sex = $("#sex").val();
    if(id==""||name==""||username==""||sex=="")
    {
        alert("数据不能为空！");
    }
    else{
        $.post("/doctor/edit", {
            id:id,
            username:username,
            name:name,
            department:department,
            sex:sex
        }, function (data) {
            if(data=="1"){
                alert("该工号已被占用！");
            }
            else {
                window.location = "/admin/doctor";
            }
        });
    }
}

function editMedicine() {
    var id = $("#id").val();
    var name = $("#name").val();
    var price = $("#price").val();
    var num = $("#num").val();
    if(name==""||price==""||num=="")
    {
        alert("数据不能为空！");
    }
    else{
        $.post("/medicine/edit",{
            id:id,
            name:name,
            price:price,
            num:num
        },function (data) {
            if(data=="1"){
                alert("该药品已经存在！");
            }
            else{
                window.location = "/admin/medicine";
            }
        });
    }
}

function findDoctor(id) {
    $.post("/doctor/find", {
        id:id
    }, function (data) {
        $("#id").val(data.id);
        $("#username").val(data.username);
        $("#name").val(data.name);
        $("#department").val(data.department);
        $("#sex").val(data.sex);
    },'json');
    $('#myModal2').modal('show');
}

function findMedicine(id) {
    $.post("/medicine/find",{
        id:id
    },function (data) {
        $("#id").val(data.id);
        $("#name").val(data.name);
        $("#price").val(data.price);
        $("#num").val(data.num);
    },'json');
    $('#myModal2').modal('show');

}

function findPatient(id) {
    $.post("/patient/find",{
        id:id
    },function (data) {
        $("#editId").val(data.id);
        $("#editName").val(data.name);
        $("#editBirthday").val(data.birthday);
        $("#editSex").val(data.sex);
        $("#editTel").val(data.phone);
    },'json');
    $('#myModal2').modal('show');
}

function findCase(id) {
    $.post("/case/find",{
        id:id
    },function (data) {
        $("#editId").val(data.id);
        $("#editDoctorId").val(data.doctorId);
        $("#editPatientName").val(data.patientName);
        $("#editDate").val(data.date);
        $("#editIll").val(data.ill);
        $("#editRemark").val(data.remark);
    },'json');
    $('#myModal2').modal('show');
}

//全选
function allcheck123() {
    var nn = $("#allboxs").is(":checked"); //判断th中的checkbox是否被选中，如果被选中则nn为true，反之为false
    if(nn == true) {
        var namebox = $("input[name^='boxs']"); //获取name值为boxs的所有input
        for(i = 0; i < namebox.length; i++) {
            namebox[i].checked=true; //js操作选中checkbox
        }
    }
    if(nn == false) {
        var namebox = $("input[name^='boxs']");
        for(i = 0; i < namebox.length; i++) {
            namebox[i].checked=false;
        }
    }
};
function delDoctors(){
    debugger;
    var check = [];//定义一个空数组
    $("#editable-sample").find(":checkbox:checked").each(function(){
        var val = $(this).parent().next().text();
        if(val!="编号"){
            check.push(val);
        }
    });
    var msg = "确认要删除这些医生信息吗？";
    if (confirm(msg)==true){
        var str = check.join(',');
        $.post("/doctor/delDoctors", {
            str:str
        }, function () {
            window.location = "/admin/doctor";
        });
    }else{
        return false;
    }
};

function delMedicines() {
    debugger;
    var check = [];
    $("#editable-sample").find(":checkbox:checked").each(function(){
        var val = $(this).parent().next().text();
        if(val!="编号"){
            check.push(val);
        }
    });
    var msg = "确认要删除这些药品信息吗？";
    if (confirm(msg)==true){
        var str = check.join(',');
        $.post("/medicine/delMedicines", {
            str:str
        }, function () {
            window.location = "/admin/medicine";
        });
    }else{
        return false;
    }
}

function delPatients() {
    debugger;
    var check = [];
    $("#editable-sample").find(":checkbox:checked").each(function(){
        var val = $(this).parent().next().text();
        if(val!="编号"){
            check.push(val);
        }
    });
    var msg = "确认要删除这些患者信息吗？";
    if (confirm(msg)==true){
        var str = check.join(',');
        $.post("/patient/delPatients", {
            str:str
        }, function () {
            window.location = "/doctor/patient";
        });
    }else{
        return false;
    }
}

function delCases() {
    debugger;
    var check = [];
    $("#editable-sample").find(":checkbox:checked").each(function(){
        var val = $(this).parent().next().text();
        if(val!="编号"){
            check.push(val);
        }
    });
    var msg = "确认要删除这些病例信息吗？";
    if (confirm(msg)==true){
        var str = check.join(',');
        $.post("/case/delCases", {
            str:str
        }, function () {
            window.location = "/doctor/case";
        });
    }else{
        return false;
    }
}

//批量添加教师到项目中
function Addexam() {
    // var file = $("#uploadFile").val();
    // var fileName = getFileName(file);
    // alert(fileName)
    alert("哈哈哈哈")
    var uploadFile=document.getElementById("uploadFile");
    alert(typeof(uploadFile))
    alert(uploadFile)
    var filename=file.getName();
    alert(filename)
    // var filename=$(uploadFile).filebox('getValue');
    // alert(filename)
    // var str=filename.split(".");
    // alert(str)
    // if(str[1]!="xls"&&str[1]!="xlsx") {
    //     $.messager.alert("错误", '只能上传Excel文件！', 'error');
    //     return;
    // }
    // var add="/batchAdditionTeacherToEnchange";
    // $('#upload').form('submit',{
    //     url: add,
    //     onSubmit: function(){
    //     },
    //     success: function(result){
    //         var result = eval('('+result+')');
    //         if (result.success=="true"){
    //             $('#uploadBatchAddEnchangeTeacher').window('close');
    //             $('#aa1').datagrid('reload');
    //             $.messager.show({
    //                 title: 'Success',
    //                 msg: '文件上传成功！'
    //             });
    //         } else {
    //             $.messager.show({
    //                 title: 'Error',
    //                 msg: '文件上传错误！'
    //             });
    //         }
    //     }
    // });
}
