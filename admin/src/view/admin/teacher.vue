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

    <div class="row">
      <div v-for="teacher in teachers" v-bind:key='teacher.index' class="col-md-3 center">
        <div>
          <span class="profile-picture">
            <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty" src="/static/image/teacherImage/image1.jpg" v-bind:title="teacher.intro"/>
            <img v-show="teacher.image" class="media-object editable img-responsive editable-click editable-empty" v-bind:src="teacher.image" v-bind:title="teacher.intro"/>
          </span>

          <div class="space-4"></div>

          <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
            <div class="inline position-relative">
              <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                <i class="ace-icon fa fa-circle light-green"></i>
                &nbsp;
                <span class="white">{{teacher.position}}</span>
              </a>
            </div>
          </div>
        </div>

        <div class="space-6"></div>

        <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
          <i class="ace-icon fa fa-user"></i>
          {{teacher.name}}【{{teacher.nickname}}】
        </a>

        <div class="space-6"></div>

        <div class="profile-social-links align-center">
          <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          &nbsp;
          <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>

        <div class="hr hr16 dotted"></div>

      </div>
    </div>

    <div
      id="add-teacher-modal-form"
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
                  <input v-model="teacher.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">nickname</label>
                <div class="col-sm-10">
                  <input v-model="teacher.nickname" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">image</label>
                <div class="col-sm-10">
                  <file v-bind:id="'image-upload'"
                        v-bind:text="'上传头像'"
                        v-bind:suffixs="['jpg', 'jpeg', 'png']"
                        v-bind:use="FILE_USE.TEACHER.key"
                        v-bind:after-upload="afterUpload"></file>
                  <div v-show="teacher.image" class="row">
                    <div class="col-md-4">
                      <img v-bind:src="teacher.image" class="img-responsive">
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">position</label>
                <div class="col-sm-10">
                  <input v-model="teacher.position" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">motto</label>
                <div class="col-sm-10">
                  <input v-model="teacher.motto" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">intro</label>
                <div class="col-sm-10">
                  <textarea v-model="teacher.intro" class="form-control" rows="5"></textarea>
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
import File from "../../components/file";
export default {
  components: { Pagination, File },
  name: "business-teacher",
  data: function () {
    return {
      teachers: [],
      teacher: {},
      FILE_USE: FILE_USE
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('business-teacher-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this;
      _this.teacher = {};
      $("#add-teacher-modal-form").modal("show");
    },

    edit(teacher) {
      let _this = this;
      // using parameter teacher directly will afffect table displayed in page, thus make a deep copy for parameter teacher
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.teacher = $.extend({}, teacher);
      $("#add-teacher-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/business/admin/teacher/list", {
          page: page,
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.teachers = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (1 != 1
        || !Validator.require(_this.teacher.name, "name")
        || !Validator.length(_this.teacher.name, "name", 1, 50)
        || !Validator.length(_this.teacher.nickname, "nickname", 1, 50)
        || !Validator.length(_this.teacher.image, "image", 1, 100)
        || !Validator.length(_this.teacher.position, "position", 1, 50)
        || !Validator.length(_this.teacher.motto, "motto", 1, 50)
        || !Validator.length(_this.teacher.intro, "intro", 1, 500)
      ) {
        return;
      }

      Loading.show();
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/business/admin/teacher/save",
          _this.teacher
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-teacher-modal-form").modal("hide");
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
          .delete("http://127.0.0.1:9000/business/admin/teacher/delete/" + id)
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

    afterUpload(resp) {
      let _this = this;
      let image = resp.content.path;
      _this.teacher.image = image;
    },
  },
};
</script>