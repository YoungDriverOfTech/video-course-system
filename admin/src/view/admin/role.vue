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
          <th>character</th>
          <th>description</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="role in roles" v-bind:key="role.index">
            <td>{{role.id}}</td>
            <td>{{role.name}}</td>
            <td>{{role.desc}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="editUser(role)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-user bigger-120"></i>
              </button>
              <button v-on:click="editResource(role)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-list bigger-120"></i>
              </button>
              <button v-on:click="edit(role)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button v-on:click="del(role.id)" class="btn btn-xs btn-danger">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>
          </td>
          </tr>
      </tbody>
    </table>

    <div
      id="add-role-modal-form"
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
                <label class="col-sm-2 control-label">character</label>
                <div class="col-sm-10">
                  <input v-model="role.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">description</label>
                <div class="col-sm-10">
                  <input v-model="role.desc" class="form-control">
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

    <!-- role resource relationship -->
    <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Role And Resource</h4>
          </div>
          <div class="modal-body">
            <ul id="tree" class="ztree"></ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              close
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveResource()">
              <i class="ace-icon fa fa-plus blue"></i>
              save
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!-- role-user relationship -->
    <div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Role And User </h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-6">
                <table id="user-table" class="table table-hover">
                  <tbody>
                  <tr v-for="user in users" v-bind:key="user.index">
                    <td>{{user.loginName}}</td>
                    <td class="text-right">
                      <a href="javascript:;" class="">
                        <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                      </a>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
              <div class="col-md-6">
                <table id="role-user-table" class="table table-hover">
                  <tbody>
                  <tr v-for="user in roleUsers" v-bind:key="user.index">
                    <td>{{user.loginName}}</td>
                    <td class="text-right">
                      <a href="javascript:;" class="">
                        <i class="ace-icon fa fa-trash blue"></i>
                      </a>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              close
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveUser()">
              <i class="ace-icon fa fa-plus blue"></i>
              save
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
import Pagination from "../../components/pagination";
export default {
  components: { Pagination },
  name: "system-role",
  data: function () {
    return {
      roles: [],
      role: {},
      resources: [],
      zTree: {},
      users: [],
      roleUsers: []
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('system-role-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this;
      _this.role = {};
      $("#add-role-modal-form").modal("show");
    },

    edit(role) {
      let _this = this;
      // using parameter role directly will afffect table displayed in page, thus make a deep copy for parameter role
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.role = $.extend({}, role);
      $("#add-role-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/system/admin/role/list", {
          page: page,
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.roles = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (1 != 1
        || !Validator.require(_this.role.name, "character")
        || !Validator.length(_this.role.name, "character", 1, 50)
        || !Validator.require(_this.role.desc, "description")
        || !Validator.length(_this.role.desc, "description", 1, 100)
      ) {
        return;
      }

      Loading.show();
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/system/admin/role/save",
          _this.role
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-role-modal-form").modal("hide");
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
          .delete("http://127.0.0.1:9000/system/admin/role/delete/" + id)
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

    editResource(role) {
      let _this = this;
      _this.role = $.extend({}, role);
      _this.loadResource();
      $("#resource-modal").modal("show");
    },

    loadResource() {
      let _this = this;
      Loading.show();
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/resource/load-tree').then((res)=>{
        Loading.hide();
        let response = res.data;
        _this.resources = response.content;
        _this.initTree();
        _this.listRoleResource();
      })
    },

    initTree() {
      let _this = this;
      let setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "",
            enable: true
          }
        }
      };

      _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
      _this.zTree.expandAll(true);
    },

    saveResource() {
      let _this = this;
      let resources = _this.zTree.getCheckedNodes();

      let resourceIds = [];
      for (let i = 0; i < resources.length; i++) {
        resourceIds.push(resources[i].id);
      }

      _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save-resource', {
        id: _this.role.id,
        resourceIds: resourceIds
      }).then((response)=>{
        let resp = response.data;
        if (resp.success) {
          Toast.success("saved");
        } else {
          Toast.warning(resp.message);
        }
      });
    },

    listRoleResource() {
      let _this = this;
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/role/list-resource/' + _this.role.id).then((response)=>{
        let resp = response.data;
        let resources = resp.content;

        _this.zTree.checkAllNodes(false);
        for (let i = 0; i < resources.length; i++) {
          let node = _this.zTree.getNodeByParam("id", resources[i]);
          _this.zTree.checkNode(node, true);
        }
      });
    },

    editUser(role) {
      let _this = this;
      _this.role = $.extend({}, role);
      _this.listUser();
      $("#user-modal").modal("show");
    },

    listUser() {
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/list', {
        page: 1,
        size: 9999
      }).then((response)=>{
        let resp = response.data;
        if (resp.success) {
          _this.users = resp.content.list;
        } else {
          Toast.warning(resp.message);
        }
      })
    },
  },
};
</script>