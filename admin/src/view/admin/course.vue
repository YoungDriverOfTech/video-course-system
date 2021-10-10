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

    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

    <div class="row">
      <div v-for="course in courses" class="col-md-4" v-bind:key="course.index">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object w-100" src="/static/image/demo-course.jpg" />
          <img v-show="course.image" class="media-object" v-bind:src="course.image" />
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">
                {{COURSE_LEVEL | optionKV(course.level)}}
              </span>
              <span class="pull-right label label-primary info-label">
                {{COURSE_CHARGE | optionKV(course.charge)}}
              </span>
              <span class="pull-right label label-primary info-label">
                {{COURSE_STATUS | optionKV(course.status)}}
              </span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{course.name}}</a>
            </h3>

            <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})" v-bind:key='teacher.index' class="profile-activity clearfix">
              <div>
                <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                <a class="user" href="#"> {{teacher.name}} </a>
                <br>
                {{teacher.position}}
              </div>
            </div>

            <p>
              <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>&nbsp;
            </p>
            <p>{{course.summary}}</p>
            <p>
              <span class="badge badge-info">{{course.id}}</span>
              <span class="badge badge-info">sort: {{course.sort}}</span>
              <span class="badge badge-info">{{course.time | formatSecond}}</span>
            </p>
            <p>
              <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                Chapter
              </button>&nbsp;
              <button v-on:click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                content
              </button>&nbsp;
              <button v-on:click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                Order
              </button>&nbsp;
              <button v-on:click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                edit
              </button>&nbsp;
              <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                delete
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>

    <div
      id="add-course-modal-form"
      class="modal fade"
      tabindex="-1"
      role="dialog">

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
                <label class="col-sm-2 control-label">
                  分类
                </label>
                <div class="col-sm-10">
                  <ul id="tree" class="ztree"></ul>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">封面</label>
                <div class="col-sm-10">
                  <file v-bind:input-id="'image-upload'"
                        v-bind:text="'上传封面'"
                        v-bind:suffixs="['jpg', 'jpeg', 'png']"
                        v-bind:use="FILE_USE.COURSE.key"
                        v-bind:after-upload="afterUpload"></file>
                  <div v-show="course.image" class="row">
                    <div class="col-md-6">
                      <img v-bind:src="course.image" class="img-responsive">
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">name</label>
                <div class="col-sm-10">
                  <input v-model="course.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">Teacher</label>
                <div class="col-sm-10">
                  <select v-model="course.teacherId" class="form-control">
                    <option v-for="o in teachers" v-bind:value="o.id" v-bind:key="o.index">{{o.name}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">summary</label>
                <div class="col-sm-10">
                  <textarea v-model="course.summary" class="form-control"></textarea>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">video length</label>
                <div class="col-sm-10">
                  <input v-model="course.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">price</label>
                <div class="col-sm-10">
                  <input v-model="course.price" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">level </label>
                <div class="col-sm-10">
                  <select v-model="course.level" class="form-control">
                    <option v-for="o in COURSE_LEVEL" v-bind:value="o.key" v-bind:key="o.index">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">charge </label>
                <div class="col-sm-10">
                  <select v-model="course.charge" class="form-control">
                    <option v-for="o in COURSE_CHARGE" v-bind:value="o.key" v-bind:key="o.index">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">status </label>
                <div class="col-sm-10">
                  <select v-model="course.status" class="form-control">
                    <option v-for="o in COURSE_STATUS" v-bind:value="o.key" v-bind:key="o.index">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">enroll number</label>
                <div class="col-sm-10">
                  <input v-model="course.enroll" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">sort order</label>
                <div class="col-sm-10">
                  <input v-model="course.sort" class="form-control" disabled>
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
    
    <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog" style="overflow:auto;">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">edit course content</h4>
          </div>
          <div class="modal-body">
            <file v-bind:input-id="'content-file-upload'"
                  v-bind:text="'上传文件1'"
                  v-bind:suffixs="['jpg', 'jpeg', 'png', 'mp4']"
                  v-bind:use="FILE_USE.COURSE.key"
                  v-bind:after-upload="afterUploadContentFile"></file>
            <br>
            <table id="file-table" class="table  table-bordered table-hover">
              <thead>
              <tr>
                <th>name</th>
                <th>address</th>
                <th>size</th>
                <th>operation</th>
              </tr>
              </thead>

              <tbody>
              <tr v-for="f in files" v-bind:key="f.id">
                <td>{{f.name}}</td>
                <td>{{f.url}}</td>
                <td>{{f.size | formatFileSize}}</td>
                <td>
                  <button v-on:click="delFile(f)" class="btn btn-white btn-xs btn-warning btn-round">
                    <i class="ace-icon fa fa-times red2"></i>
                    delete
                  </button>
                </td>
              </tr>
              </tbody>
            </table>
            <form class="form-horizontal">
               <div class="form-group">
                <div class="col-lg-12">
                  {{saveContentLabel}}
                </div>
              </div>
              <div class="form-group">
                <div class="col-lg-12">
                  <div id="content"></div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              Cancel
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveContent()">
              <i class="ace-icon fa fa-plus blue"></i>
              Save
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Order</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-lg-3">
                  Current Order
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-lg-3">
                  New Order
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.newSort" name="newSort">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              Cancle
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
              <i class="ace-icon fa fa-plus blue"></i>
              Update Order
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
import Pagination from "../../components/pagination";
import File from "../../components/file";
export default {
  components: { Pagination, File },
  name: "business-course",
  data: function () {
    return {
      courses: [],
      course: {},
      COURSE_LEVEL: COURSE_LEVEL,
      COURSE_CHARGE: COURSE_CHARGE,
      COURSE_STATUS: COURSE_STATUS,
      FILE_USE: FILE_USE,
      categorys: [],
      tree: {},
      saveContentLabel: "",
      sort: {
        id: '',
        olsSort: 0,
        newSort: 0
      },
      teachers: [],
      files: [],
    };
  },
  mounted: function () {
    // this.$parent.activeSidebar('business-course-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.allCategory();
    _this.allTeacher();
    _this.list(1);
    
  },
  methods: {
    add() {
      let _this = this;
      _this.course = {
        sort: _this.$refs.pagination.total +1
      };
      _this.tree.checkAllNodes(false);
      $("#add-course-modal-form").modal("show");
    },

    edit(course) {
      let _this = this;
      // using parameter course directly will afffect table displayed in page, thus make a deep copy for parameter course
      // $.extend(target, source);  this deep copy method is provided by jquery
      _this.course = $.extend({}, course);
      _this.listCategory(course.id);
      $("#add-course-modal-form").modal("show");
    },

    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/business/admin/course/list", {
          page: page,
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.courses = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    save() {
      let _this = this;

      // require and length check
      if (1 != 1
        || !Validator.require(_this.course.name, "name")
        || !Validator.length(_this.course.name, "name", 1, 50)
        || !Validator.length(_this.course.summary, "summary", 1, 2000)
        || !Validator.length(_this.course.image, "profile", 1, 100)
      ) {
        return;
      }

      let categorys = _this.tree.getCheckedNodes();
      if (Tool.isEmpty(categorys)) {
        Toast.warning('Please select one category at lease');
        return;
      }
      _this.course.categorys = categorys;

      Loading.show();
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/business/admin/course/save",
          _this.course
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#add-course-modal-form").modal("hide");
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
          .delete("http://127.0.0.1:9000/business/admin/course/delete/" + id)
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

    // jump to chapter page
    toChapter(course) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_COURSE, course);
      _this.$router.push('/business/chapter');
    },

    allCategory() {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post("http://127.0.0.1:9000/business/admin/category/all")
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.categorys = resp.content;
          _this.initTree();
        });
    },

    initTree() {
      let _this = this;
      let setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            idKey: 'id',
            pIdKey: 'parent',
            rootPId: '00000000',
            enable: true
          }
        }
      };

      let zNodes = _this.categorys;

      _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);

      // expand all category check nodes
      _this.tree.expandAll(true);
    },

    listCategory(courseId) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' + courseId).then((res)=>{
        Loading.hide();
        let response = res.data;
        let categorys = response.content;

        // check all nodes which exists in db
        _this.tree.checkAllNodes(false);
        for (let i = 0; i < categorys.length; i++) {
          let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
          _this.tree.checkNode(node, true);
        }
      })
    },

    // open the editor
    editContent(course) {
      let _this = this;
      let id = course.id;
      _this.course = course;
      $('#content').summernote({
        focus: true,
        height: 300
      });

      // clear history text
      $('#content').summernote('code', '');
      _this.saveContentLabel = '';
      Loading.show();

      // get course content file
      _this.listContentFiles();

      // get the course content by courseId
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/' + id)
        .then((response) => {
          Loading.hide();
          let resp = response.data;

          if (resp.success) {
            $("#course-content-modal").modal({backdrop: 'static', keyboard: false});
            if (resp.content) {
              $('#content').summernote('code', resp.content.content);
            }

            // schedule auto save course content
            let saveContentInterval = setInterval(() => {
              _this.saveContent();
            }, 5000);

            // remove the auto save schedule when close edit dialog
            $('#course-content-modal').on('hidden.bs.modal', function() {
              clearInterval(saveContentInterval);
            })

          } else {
            Toast.warning(resp.message);
          }
        });
    },

    // save content text
    saveContent() {
      let _this = this;
      let content = $('#content').summernote('code');
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
        id: _this.course.id,
        content: content
      }).then(response => {
        Loading.hide();
        let resp = response.data;
        if (resp.success) {
          let now = Tool.dateFormat('mm:ss');
          _this.saveContentLabel = 'last update time: ' + now;
        } else {
          Toast.warning(resp.message);
        }
      });
    },

    openSortModal(course) {
      let _this = this;
      _this.sort = {
        id: course.id,
        oldSort: course.sort,
        newSort: course.sort
      };
      $('#course-sort-modal').modal('show');
    },

    updateSort() {
      let _this = this;
      if (_this.sort.newSort === _this.sort.olsSort) {
        Toast.warning('No change in sort')
        return;
      }
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/sort", _this.sort).then((res) => {
          let response = res.data;

          if (response.success) {
            Toast.success("sort updated");
            $("#course-sort-modal").modal("hide");
            _this.list(1);
          } else {
            Toast.error("sort update fail");
          }
        });
    },

    allTeacher() {
      let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/all').then((response)=>{
          Loading.hide();
          let resp = response.data;
          _this.teachers = resp.content;
        })
    },

    afterUpload(resp) {
      let _this = this;
      let image = resp.content.path;
      _this.course.image = image;
    },

    listContentFiles() {
      let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/list/' + _this.course.id).then((response)=>{
          let resp = response.data;
          if (resp.success) {
            _this.files = resp.content;
          }
        });
    },

    afterUploadContentFile(response) {
      let _this = this;
      let file = response.content;
      file.courseId = _this.course.id;
      file.url = file.path;
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/save', file).then((response)=>{
        let resp = response.data;
        if (resp.success) {
          Toast.success("uploaded");
          _this.files.push(resp.content);
        }
      });
    },

    delFile(f) {
      let _this = this;
      Confirm.show("This opertion is not revertable, sure to do it?", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/delete/' + f.id).then((response)=>{
          let resp = response.data;
          if (resp.success) {
            Toast.success("deleted");
            Tool.removeObj(_this.files, f);
          }
        });
      });
    },
  },
};
</script>

<style scoped>
  .caption h3 {
    font-size: 20px;
  }

  .w-100 {
    width: 100%;
  }

  @media (max-width: 1199px) {
    .caption h3 {
      font-size: 16px;
    }
  }
</style>