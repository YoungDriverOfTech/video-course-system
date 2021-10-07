<template>
  <div>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <pagination
      ref="pagination"
      v-bind:list="list"
      v-bind:itemCount="8"
    ></pagination>

    <table id="simple-table" class="table table-bordered table-hover">
      <thead>
        <tr>
          <th>id</th>
          <th>relative path</th>
          <th>file name</th>
          <th>suffix</th>
          <th>size</th>
          <th>application</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="file in files" v-bind:key="file.index">
            <td>{{file.id}}</td>
            <td>{{file.path}}</td>
            <td>{{file.name}}</td>
            <td>{{file.suffix}}</td>
            <td>{{file.size}}</td>
            <td>{{ FILE_USE | optionKV(file.use) }}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(file)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button v-on:click="del(file.id)" class="btn btn-xs btn-danger">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>
          </td>
          </tr>
      </tbody>
    </table>

    <div
      id="add-file-modal-form"
      class="modal fade"
      tabindex="-1"
      role="dialog"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">relative path</label>
                <div class="col-sm-10">
                  <input v-model="file.path" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">file name</label>
                <div class="col-sm-10">
                  <input v-model="file.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">suffix</label>
                <div class="col-sm-10">
                  <input v-model="file.suffix" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">size</label>
                <div class="col-sm-10">
                  <input v-model="file.size" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">application</label>
                <div class="col-sm-10">
                  <select v-model="file.use" class="form-control">
                    <option v-for="o in FILE_USE" v-bind:value="o.key" v-bind:key='o.key'>{{o.value}}</option>
                  </select>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">
              Cancel
            </button>
            <button v-on:click="save()" type="button" class="btn btn-primary">
              Save
            </button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
  </div>
</template>

<script>
import Pagination from "../../components/pagination";
export default {
  components: { Pagination },
  name: "file-file",
  data: function () {
    return {
      files: [],
      file: {},
      FILE_USE: FILE_USE,
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('file-file-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this;
      _this.file = {};
      $("#add-file-modal-form").modal("show");
    },

    edit(file) {
      let _this = this;
      // using parameter file directly will afffect table displayed in page, thus make a deep copy for parameter file
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.file = $.extend({}, file);
      $("#add-file-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post(process.env.VUE_APP_SERVER + "/file/admin/file/list", {
          page: page,
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.files = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (1 != 1
        || !Validator.require(_this.file.path, "relative path")
        || !Validator.length(_this.file.path, "relative path", 1, 100)
        || !Validator.length(_this.file.name, "file name", 1, 100)
        || !Validator.length(_this.file.suffix, "suffix", 1, 10)
      ) {
        return;
      }

      Loading.show();
      _this.$ajax
        .post(
          process.env.VUE_APP_SERVER + "/file/admin/file/save",
          _this.file
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-file-modal-form").modal("hide");
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
          .delete(process.env.VUE_APP_SERVER + "/file/admin/file/delete/" + id)
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
  },
};
</script>