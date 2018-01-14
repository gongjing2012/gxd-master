njh mkj8u7y65tr4e3wsqa<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>上传图片测试</title>
</head>
<body>
<div>
    <span class="reg_label2 rt50 relativeBox"><span class="red_font">*</span>图片：</span>
    <span class="upload_span relativeBox">
<input type="file" id="id_license" class="input_file absoluteBox notnull" name="license" />
<img src=""  id="id_license_img"  />
<input type="hidden"  id="id_license_value" name="licenseName"  />
</span>
</div>
</body>
<script src="../../static/js/jquery-2.1.1.js"></script>
<script src="../../static/js/ajaxfileupload.js"></script>
<script>
    $("#id_license").on("change", function(){
        $.ajaxFileUpload({
            url: '/file/upload', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'id_license', //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            data:{param:'license'},//file 标签的名称
            type:'post',
            success: function (data, status)  //服务器成功响应处理函数
            {
                if(data.result == "true"){
                    alert("上传成功！");
                    $("#id_license_value").val(data.srckey);

                    var dataURL = data.srckey;
                    var $img = $("#id_license_img");
                    $img.attr('src', dataURL);
                }else {
                    alert("上传失败！");
                }
            },error: function (data, status, e)//服务器响应失败处理函数
            {
                alert(e);
            }
        })

    });

</script>
</html>