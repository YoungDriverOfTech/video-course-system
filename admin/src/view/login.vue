<template>
  <div class="main-container">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1>
                <i class="ace-icon fa fa-leaf green"></i>
                <span>Admin Login</span>
              </h1>
            </div>

            <div class="space-6"></div>

            <div class="position-relative">
              <div
                id="login-box"
                class="login-box visible widget-box no-border"
              >
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="ace-icon fa fa-coffee green"></i>
                      Please Enter Your Information
                    </h4>

                    <div class="space-6"></div>

                    <form>
                      <fieldset>
                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <input type="text" v-model="user.loginName" class="form-control" placeholder="Username"/>
                            <i class="ace-icon fa fa-user"></i>
                          </span>
                        </label>

                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <input type="password" v-model="user.password" class="form-control" placeholder="Password"/>
                            <i class="ace-icon fa fa-lock"></i>
                          </span>
                        </label>

                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <div class="input-group">
                              <input v-model="user.imageCode" type="text" class="form-control" placeholder="certification">
                              <span class="input-group-addon" id="basic-addon2">
                                <img v-on:click="loadImageCode()" id="image-code" alt="certification"/>
                              </span>
                            </div>
                          </span>
                        </label>

                        <div class="space"></div>

                        <div class="clearfix">
                          <label class="inline">
                            <input v-model="remember" type="checkbox" class="ace" />
                            <span class="lbl"> Remember Me</span>
                          </label>

                          <button
                            type="button"
                            class="width-35 pull-right btn btn-sm btn-primary"
                            v-on:click="login()"
                          >
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110">Login</span>
                          </button>
                        </div>

                        <div class="space-4"></div>
                      </fieldset>
                    </form>
                  </div>
                  <!-- /.widget-main -->
                </div>
                <!-- /.widget-body -->
              </div>
              <!-- /.login-box -->
            </div>
            <!-- /.position-relative -->
          </div>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </div>
    <!-- /.main-content -->
  </div>
  <!-- /.main-container -->
</template>

<script>

export default {
    name: 'login',
    data: function() {
      return {
        user: {},
        remember: true,
        imageCodeToken: ""
      }
    },
    mounted: function() {
      let _this = this;
      $('body').removeClass('no-skin');
      $('body').attr('class', 'login-layout light-login');

      // get username and password from localstorage if exist
      let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
      if (rememberUser) {
        _this.user = rememberUser;
      }

      // load certification image during the initial
      _this.loadImageCode();
    },

    methods: {
        login() {
          let _this = this;

          let md5 = hex_md5(_this.user.password);
          let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
          if (md5 !== rememberUser.md5) {
            _this.user.password = hex_md5(_this.user.password + KEY);
          }

          _this.user.imageCodeToken = _this.imageCodeToken;

          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', _this.user).then((response)=>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              let loginUser = resp.content;
              Tool.setLoginUser(resp.content);

              if (_this.remember) {
                // save encoded password and md5 for justify whether password is changed
                let md5 = hex_md5(_this.user.password); 
                LocalStorage.set(LOCAL_KEY_REMEMBER_USER, {
                  loginName: loginUser.loginName,
                  password: _this.user.password,
                  md5: md5
                });
              } else {
                // clear localstorage if remember me is not checked, next time will not show username and password
                LocalStorage.set(LOCAL_KEY_REMEMBER_USER, null);
              }
              _this.$router.push("/welcome")
              console.log('122222222222222222222')
            } else {
              Toast.warning(resp.message);
              _this.user.password = '';
              _this.loadImageCode();
            }
          });
        },

        loadImageCode() {
          let _this = this;
          _this.imageCodeToken = Tool.uuid(8);
          $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
        },
    }
}
</script>

<style scoped>
  .input-group-addon {
    padding: 0;
  }
</style>