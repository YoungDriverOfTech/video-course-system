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
        let formData = new window.FormData();
        let file = _this.$refs.file.files[0];

        // generate file key that is used to mark whether some sections belongs to one file
        let key = hex_md5(file);
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
        let shardSize = 5 * 1024 * 1024;    // unit 5MB
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

        _this.upload(param);
      },  

      upload() {
        let _this = this;
        let shardIndex = param.shardIndex;
        let shardTotal = param.shardTotal;
        let shardSize = param.shardSize;
        let fileShard = _this.getFileShard(shardIndex, shardSize);

        // parse file into base64 and transfer to backend
        let fileReader = new FIleReader();
        fileReader.onload = e => {
          let base64 = e.target.result;
          param.shard = base64;

          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', param).then((response) => {
            Loading.hide();
            let resp = response.data;
            if (shardIndex < shardTotal) {
              // upload next section
              param.shardIndex = param.shardIndex + 1;
              _this.upload(param);
            } else {
              _this.afterUpload(resp);
              $("#" + _this.inputId + "-input").val("");
            }
          });
        };
        fileReader.readAsDataURL(fileShard);
      },

      getFileSHard() {
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
