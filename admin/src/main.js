import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from 'axios'
import filter from './filter/filter'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;

// let session keep available both in frontend and
axios.defaults.withCredentials = true;

/**
 * axios interceptor
 */
axios.interceptors.request.use(function (config) {
  let token = Tool.getLoginUser().token;
  if (Tool.isNotEmpty(token)) {
    config.headers.token = token;
  }
  // console.log("request:", config);
  return config;
}, error => { console.log(error) });
axios.interceptors.response.use(function (response) {
  // console.log("response:", response);
  return response;
}, error => { console.log(error) });

// global filter
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key]);
});


// router filter
router.beforeEach((to, from, next) => {
  // check meta.loginRequire attribute
  if (to.matched.some(function(item){
    return item.meta.loginRequire
  })) {
    let loginUser = Tool.getLoginUser();
    if (Tool.isEmpty(loginUser)) {
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
