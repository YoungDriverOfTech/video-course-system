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
          <th>ID</th>
          <th>title</th>
          <th>course.id</th>
          <th>chapter.id</th>
          <th>video</th>
          <th>video length</th>
          <th>is free or not</th>
          <th>sort</th>
          <th>created_at</th>
          <th>updated_at</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="section in sections" v-bind:key="section.index">
            <td>{{section.id}}</td>
            <td>{{section.title}}</td>
            <td>{{section.courseId}}</td>
            <td>{{section.chapterId}}</td>
            <td>{{section.video}}</td>
            <td>{{section.time}}</td>
            <td>{{section.charge}}</td>
            <td>{{section.sort}}</td>
            <td>{{section.createdAt}}</td>
            <td>{{section.updatedAt}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(section)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>
          </td>
          </tr>
      </tbody>
    </table>

    <div
      id="add-section-modal-form"
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
                <label class="col-sm-2 control-label">ID</label>
                <div class="col-sm-10">
                  <input v-model="section.id" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">title</label>
                <div class="col-sm-10">
                  <input v-model="section.title" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">course.id</label>
                <div class="col-sm-10">
                  <input v-model="section.courseId" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">chapter.id</label>
                <div class="col-sm-10">
                  <input v-model="section.chapterId" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">video</label>
                <div class="col-sm-10">
                  <input v-model="section.video" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">video length</label>
                <div class="col-sm-10">
                  <input v-model="section.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">is free or not</label>
                <div class="col-sm-10">
                  <input v-model="section.charge" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">sort</label>
                <div class="col-sm-10">
                  <input v-model="section.sort" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">created_at</label>
                <div class="col-sm-10">
                  <input v-model="section.createdAt" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">updated_at</label>
                <div class="col-sm-10">
                  <input v-model="section.updatedAt" class="form-control">
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
  name: "section",
  data: function () {
    return {
      sections: [],
      section: {},
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('business-section-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this;
      _this.section = {};
      $("#add-section-modal-form").modal("show");
    },

    edit(section) {
      let _this = this;
      // using parameter section directly will afffect table displayed in page, thus make a deep copy for parameter section
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.section = $.extend({}, section);
      $("#add-section-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post(process.env.VUE_APP_SERVER + "/business/admin/section/list", {
          page: page,
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.sections = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      // if (!Validator.require(_this.section.name, "name") ||
      //   !Validator.require(_this.section.courseId, "courseId") ||
      //   !Validator.length(_this.section.courseId, "courseId", 1, 8)) {
      //   return;
      // }

      Loading.show();
      _this.$ajax
        .post(
          process.env.VUE_APP_SERVER + "/business/admin/section/save",
          _this.section
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-section-modal-form").modal("hide");
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
          .delete(process.env.VUE_APP_SERVER + "/business/admin/section/delete/" + id)
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