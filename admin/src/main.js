import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;

/**
 * axios interceptor
 */
axios.interceptors.request.use(function (config) {
  console.log("请求：", config);
  return config;
}, error => { console.log(error) });
axios.interceptors.response.use(function (response) {
  console.log("返回结果：", response);
  return response;
}, error => { console.log(error) });


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
