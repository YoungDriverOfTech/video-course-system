<template>
  <div>
    <p>
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <div class="row">
      <div class="col-md-6">
        <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="10"></textarea>
        <br>
        <button id="save-btn" type="button" class="btn btn-primary" v-on:click="save()">
          保存
        </button>
      </div>
      <div class="col-md-6">
        <ul id="tree" class="ztree"></ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {  },
  name: "system-resource",
  data: function () {
    return {
      resources: [],
      resource: {},
      resourceStr: "",
      tree: {},
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('system-resource-sidebar');
    let _this = this;
    _this.list();
  },
  methods: {
    list() {
      let _this = this;
      Loading.show();
      _this.$ajax
        .get("http://127.0.0.1:9000/system/admin/resource/load-tree")
        .then((res) => {
          Loading.hide();
           let response = res.data;
          _this.resources = response.content;
          // initiate tree
          _this.initTree();
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (Tool.isEmpty(_this.resourceStr)) {
        Toast.warning("resource is required");
        return;
      }
      let json = JSON.parse(_this.resourceStr);

      Loading.show();
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/system/admin/resource/save",
          json
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-resource-modal-form").modal("hide");
            _this.list(1);
            Toast.success("saved");
          } else {
            Toast.warning(resp.message);
          }
        });
    },

    del(id) {
      let _this = this;
      Confirm.show("Please confirm your delete operation!", function () {
        Loading.show();
        _this.$ajax
          .delete("http://127.0.0.1:9000/system/admin/resource/delete/" + id)
          .then((response) => {
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
            }
          });
        Toast.success("deleted");
      });
    },

    initTree() {
      let _this = this;
      let setting = {
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "",
            // enable: true
          }
        }
      };

      _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
      _this.zTree.expandAll(true);
    },
  },
};
</script>