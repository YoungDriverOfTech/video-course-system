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
          <th>parent id</th>
          <th>name</th>
          <th>sort</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="category in categorys" v-bind:key="category.index">
            <td>{{category.id}}</td>
            <td>{{category.parent}}</td>
            <td>{{category.name}}</td>
            <td>{{category.sort}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>
          </td>
          </tr>
      </tbody>
    </table>

    <div
      id="add-category-modal-form"
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
                <label class="col-sm-2 control-label">parent id</label>
                <div class="col-sm-10">
                  <input v-model="category.parent" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">name</label>
                <div class="col-sm-10">
                  <input v-model="category.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">sort</label>
                <div class="col-sm-10">
                  <input v-model="category.sort" class="form-control">
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
  name: "business-category",
  data: function () {
    return {
      categorys: [],
      category: {},
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('business-category-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this;
      _this.category = {};
      $("#add-category-modal-form").modal("show");
    },

    edit(category) {
      let _this = this;
      // using parameter category directly will afffect table displayed in page, thus make a deep copy for parameter category
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.category = $.extend({}, category);
      $("#add-category-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/business/admin/category/list", {
          page: page,
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.categorys = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (1 != 1
        || !Validator.require(_this.category.parent, "parent id")
        || !Validator.require(_this.category.name, "name")
        || !Validator.length(_this.category.name, "name", 1, 50)
      ) {
        return;
      }

      Loading.show();
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/business/admin/category/save",
          _this.category
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-category-modal-form").modal("hide");
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
          .delete("http://127.0.0.1:9000/business/admin/category/delete/" + id)
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