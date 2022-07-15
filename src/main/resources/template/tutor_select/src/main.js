import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false
import router from '@/router/index'

import store from '@/store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import Paganation from '@/components/Paganation/Paganation.vue'
Vue.component('Paganation',Paganation)

import Search from '@/components/Search/Search.vue'
Vue.component('Search',Search)

new Vue({
  render: h => h(App),
  beforeCreate(){
    Vue.prototype.$bus = this
  },
  router,
  store
}).$mount('#app')
