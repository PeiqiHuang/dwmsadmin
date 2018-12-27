function initEditor(id, subDir, editorObj) {
	ClassicEditor
	.create(document.querySelector("#" + id), {
        language:"zh-cn",
        ckfinder: {
        	uploadUrl: "common/upload?subDir=" + subDir 
        }
    }) 
	.then(editor => { 
		editorObj.editor = editor; 
	}) 
	.catch(error => { console.error(error); });
}
//  CKEditor 上传适配器
/*class UploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }
    upload() {
        return new Promise((resolve, reject) => {
            const data = new FormData();
            data.append('upload', this.loader.file);
            data.append('allowSize', 10);//允许图片上传的大小/兆
            $.ajax({
                url: 'loadImage',
                type: 'POST',
                data: data,
                dataType: 'json',
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.res) {
                        resolve({
                            default: data.url
                        });
                    } else {
                        reject(data.msg);
                    }

                }
            });
           
        });
    }
    abort() {
    }
}*/