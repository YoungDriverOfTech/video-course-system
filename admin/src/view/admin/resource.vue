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
          <th>name</th>
          <th>page</th>
          <th>request</th>
          <th>parent id</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="resource in resources" v-bind:key="resource.index">
            <td>{{resource.id}}</td>
            <td>{{resource.name}}</td>
            <td>{{resource.page}}</td>
            <td>{{resource.request}}</td>
            <td>{{resource.parent}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(resource)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button v-on:click="del(resource.id)" class="btn btn-xs btn-danger">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>
          </td>
          </tr>
      </tbody>
    </table>

    <div
      id="add-resource-modal-form"
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
                <label class="col-sm-2 control-label">name</label>
                <div class="col-sm-10">
                  <input v-model="resource.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">page</label>
                <div class="col-sm-10">
                  <input v-model="resource.page" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">request</label>
                <div class="col-sm-10">
                  <input v-model="resource.request" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">parent id</label>
                <div class="col-sm-10">
                  <input v-model="resource.parent" class="form-control">
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
  name: "system-resource",
  data: function () {
    return {
      resources: [],
      resource: {},
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('system-resource-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this;
      _this.resource = {};
      $("#add-resource-modal-form").modal("show");
    },

    edit(resource) {
      let _this = this;
      // using parameter resource directly will afffect table displayed in page, thus make a deep copy for parameter resource
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.resource = $.extend({}, resource);
      $("#add-resource-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/system/admin/resource/list", {
          page: page,
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.resources = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (1 != 1
        || !Validator.require(_this.resource.name, "name")
        || !Validator.length(_this.resource.name, "name", 1, 100)
        || !Validator.length(_this.resource.page, "page", 1, 50)
        || !Validator.length(_this.resource.request, "request", 1, 200)
      ) {
        return;
      }

      Loading.show();
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/system/admin/resource/save",
          _this.resource
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
  },
};
</script>