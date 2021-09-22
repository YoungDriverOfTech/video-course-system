<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
    </h4>
    <hr>
    <p>
      <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
      </router-link>
      &nbsp;
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
          <th>名称</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="chapter in chapters" :key="chapter.id">
          <td>{{ chapter.id }}</td>
          <td>{{ chapter.name }}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                Section
              </button>&nbsp;

              <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                edit
              </button>&nbsp;

              <button v-on:click="del(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
                delete
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <div
      id="add-chapter-modal-form"
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
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                  <input
                    v-model="chapter.name"
                    type="text"
                    class="form-control"
                    placeholder="Name"
                  />
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label">Course</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{course.name}}</p>
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
  name: "chapter",
  data: function () {
    return {
      chapters: [],
      chapter: {},
      course: {},
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('business-chapter-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;

    // request from course page, need to get couse infomation
    let course = SessionStorage.get(SESSION_KEY_COURSE);
    if (Tool.isEmpty(course)) {
      _this.$router.push('/welcome');
      return;
    }
    _this.course = course;

    _this.list(1);

    _this.$parent.activeSidebar("business-course-sidebar");
  },
  methods: {
    add() {
      let _this = this;
      _this.chapter = {};
      $("#add-chapter-modal-form").modal("show");
    },

    edit(chapter) {
      let _this = this;
      // using parameter chapter directly will afffect table displayed in page, thus make a deep copy for parameter chapter
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.chapter = $.extend({}, chapter);
      $("#add-chapter-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/business/admin/chapter/list", {
          page: page,
          size: _this.$refs.pagination.size,
          courseId: _this.course.id
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.chapters = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (!Validator.require(_this.chapter.name, "name") ||
        !Validator.length(_this.chapter.courseId, "courseId", 1, 8)) {
        return;
      }

      _this.chapter.courseId = _this.course.id;

      Loading.show();
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/business/admin/chapter/save",
          _this.chapter
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-chapter-modal-form").modal("hide");
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
          .delete("http://127.0.0.1:9000/business/admin/chapter/delete/" + id)
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

    // jump to section page
    toSection(chapter) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
      _this.$router.push('/business/section');
    }
  },
};
</script>