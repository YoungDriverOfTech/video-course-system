<template>
  <div>
    <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-upload"></i>
      {{text}}
    </button>
    <input class="hidden" type="file" ref="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
  </div>
</template>

<script>
  export default {
    name: 'big-file',
    props: {
      text: {
        default: "upload-big-file"
      },
      inputId: {
        default: "file-upload"
      },
      suffixs: {
        default: []
      },
      use: {
        default: ""
      },
      afterUpload: {
        type: Function,
        default: null
      },
    },
    data: function () {
      return {
      }
    },
    methods: {
      uploadFile () {
        let _this = this;
        let file = _this.$refs.file.files[0];

        // generate file key that is used to mark whether some sections belongs to one file
        let key = hex_md5(file.name + file.size + file.type);
        let key10 = parseInt(key, 16);
        let key62 = Tool._10to62(key10);

        // judege file type
        let suffixs = _this.suffixs;
        let fileName = file.name;
        let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
        let validateSuffix = false;
        for (let i = 0; i < suffixs.length; i++) {
          if (suffixs[i].toLowerCase() === suffix) {
            validateSuffix = true;
            break;
          }
        }
        if (!validateSuffix) {
          Toast.warning("File type is now allowed, please upload just as follows：" + suffixs.join(","));
          $("#" + _this.inputId + "-input").val("");
          return;
        }

        // file section
        let shardSize = 10 * 1024 * 1024;    // unit 10MB
        let shardIndex = 1;		// section index
        let size = file.size;
        let shardTotal = Math.ceil(size / shardSize); // total section number

        let param = {
          'shardIndex': shardIndex,
          'shardSize': shardSize,
          'shardTotal': shardTotal,
          'use': _this.use,
          'name': file.name,
          'suffix': suffix,
          'size': file.size,
          'key': key62
        };

        _this.check(param);
      },  

      check(param) {
        let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + '/file/admin/check/' + param.key).then((response)=>{
          let resp = response.data;
          if (resp.success) {
            let obj = resp.content;
            if (!obj) {
              param.shardIndex = 1;
              _this.upload(param);
            } else if (obj.shardIndex === obj.shardTotal) {
              // uploaded section muber == total section number, which means upload is all finished and there is no need to upload again
              Toast.success("File uploaded seccess");
              _this.afterUpload(resp);
              $("#" + _this.inputId + "-input").val("");
            } else {
              param.shardIndex = obj.shardIndex + 1;
              _this.upload(param);
            }
          } else {
            Toast.warning('upload failed');
            $("#" + _this.inputId + "-input").val("");
          }
        })
      },

      upload(param) {
        let _this = this;
        let shardIndex = param.shardIndex;
        let shardTotal = param.shardTotal;
        let shardSize = param.shardSize;
        let fileShard = _this.getFileShard(shardIndex, shardSize);
        // parse file into base64 and transfer to backend
        let fileReader = new FileReader();

        Progress.show(parseInt((shardIndex - 1) * 100 / shardTotal));
        fileReader.onload = e => {
          let base64 = e.target.result;
          param.shard = base64;

          _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', param).then((response) => {
            let resp = response.data;
            Progress.show(parseInt(shardIndex * 100 / shardTotal));
            if (shardIndex < shardTotal) {
              // upload next section
              param.shardIndex = param.shardIndex + 1;
              _this.upload(param);
            } else {
              Progress.hide();
              _this.afterUpload(resp);
              $("#" + _this.inputId + "-input").val("");
            }
          });
        };
        fileReader.readAsDataURL(fileShard);
      },

      getFileShard(shardIndex, shardSize) {
        let _this = this;
        let file = _this.$refs.file.files[0];
        let start = (shardIndex - 1) * shardSize; // current section start index
        let end = Math.min(file.size, start + shardSize); // current section end index
        let fileShard = file.slice(start, end); // extract current sction content
        return fileShard;
      },

      selectFile () {
        let _this = this;
        $("#" + _this.inputId + "-input").trigger("click");
      }
    }
  }
</script>
