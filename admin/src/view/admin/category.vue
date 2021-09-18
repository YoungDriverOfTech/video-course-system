<template>
  <div>
    <div class="row">
      <!-- First Category -->
      <div class="col-md-6">
        <p>
          <button v-on:click="add1()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增一级分类
          </button>
          &nbsp;
          <button v-on:click="all()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
          </button>
        </p>

        <table id="level1-table" class="table table-bordered table-hover">
          <thead>
            <tr>
              <th>id</th>
              <th>name</th>
              <th>sort</th>
              <th>操作</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="category in level1" v-bind:key="category.index" 
              v-on:click='onClickLevel1(category)'  v-bind:class="{'active' : category.id === active.id}">
                <td>{{category.id}}</td>
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
      </div>

      <!-- Second Category -->
      <div class="col-md-6">
        <p>
          <button v-on:click="add2()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增二级分类
          </button>
        </p>

        <table id="level2-table" class="table table-bordered table-hover">
          <thead>
            <tr>
              <th>id</th>
              <th>name</th>
              <th>sort</th>
              <th>操作</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="category in level2" v-bind:key="category.index">
                <td>{{category.id}}</td>
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
      </div>
    </div>

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
                <label class="col-sm-2 control-label">父分类</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{active.name || "无"}}</p>
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
export default {
  components: { },
  name: "business-category",
  data: function () {
    return {
      categorys: [],
      category: {},
      level1: [],
      level2:[],
      active: {}
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('business-category-sidebar');
    let _this = this;
    _this.all();
  },
  methods: {

    add1() {
      let _this = this;
      _this.active = {};
      _this.level2 = [];
      _this.category = {
        parent: '00000000'
      };
      $("#add-category-modal-form").modal("show");
    },

    add2() {
      let _this = this;
      if (Tool.isEmpty(_this.active)) {
        Toast.warning('Please select the first category');
        return;
      }
      _this.category = {
        parent: _this.active.id
      };
      $(".modal").modal("show");
    },

    edit(category) {
      let _this = this;
      // using parameter category directly will afffect table displayed in page, thus make a deep copy for parameter category
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.category = $.extend({}, category);
      $("#add-category-modal-form").modal("show");
    },

    all() {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/business/admin/category/all")
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.categorys = resp.content;

          // transfer list data into tree
          _this.level1 = [];
          for (let i = 0; i < _this.categorys.length; i++) {
            let c = _this.categorys[i];
            if (c.parent === '00000000') {
              _this.level1.push(c);
              for (let j = 0; j < _this.categorys.length; j++) {
                let child = _this.categorys[j];
                if (child.parent === c.id){
                  if (Tool.isEmpty(c.children)) {
                    c.children = [];
                  }
                  c.children.push(child);
                }
              }
            }
          }
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
            _this.all();
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
              _this.all();
            }
          });
        Toast.success("deleted");
      });
    },

    onClickLevel1(category) {
      let _this = this;
      _this.active = category;
      _this.level2 = category.children;
    }
  },
};
</script>

<style scoped>
  .active td {
    background-color: #d6e9c6 !important;
  }
</style>