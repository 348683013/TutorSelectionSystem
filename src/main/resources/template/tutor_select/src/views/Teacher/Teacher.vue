<template>
  <div class="teacher-list-wrapper">
    <div class="teacher-list-wrap">
      <div class="teacher-list">
        <Manage :tableData="tableData" :attr="attr" />
      </div>
      <transition name="el-fade-in">
        <router-view></router-view>
      </transition>
      <Paganation />
    </div>
  </div>
</template>

<script>
import Manage from "@/components/Manage/Manage.vue";
export default {
  name: "Teacher",
  components: {
    Manage,
  },
  data() {
    return {
      tableData: [
        {
          name: "张鑫南",
          number: "副教授",
          class: "生物信息",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "5",
          address: "陕西省渭南市临渭区光电高层8楼b2",
          scienceClass:'厉害'
        },
        {
          name: "张鑫南",
          number: "副教授",
          class: "生物信息",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "5",
          address: "陕西省渭南市临渭区光电高层8楼b2",
          scienceClass:'厉害'
        },
        {
          name: "张鑫南",
          number: "副教授",
          class: "生物信息",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "5",
          address: "陕西省渭南市临渭区光电高层8楼b2",
          scienceClass:'厉害'
        },
        {
          name: "张鑫南",
          number: "副教授",
          class: "生物信息",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "5",
          address: "陕西省渭南市临渭区光电高层8楼b2",
          scienceClass:'厉害'
        },
        {
          name: "张鑫南",
          number: "副教授",
          class: "生物信息",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "5",
          address: "陕西省渭南市临渭区光电高层8楼b2",
          scienceClass:'厉害'
        },
        {
          name: "张鑫南",
          number: "副教授",
          class: "生物信息",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "5",
          address: "陕西省渭南市临渭区光电高层8楼b2",
          scienceClass:'厉害'
        },
      ],
      attr: {
        name: "姓名",
        number: "职称",
        class: "专业",
        phone: "电话",
        email: "邮箱",
        status: "剩余名额",
        address: "联系地址",
        add: "添加导师",
        addEvent: () => {
          this.$router.push({
            path: "/teacher/edit",
            query: {
              isStudent: false,
              data: {
                name: "",
                number: "",
                class: "",
                phone: "",
                email: "",
                address: "",
                status: "",
                scienceClass:''
              },
            },
          });
        },
        edit: (data) => {
          this.$router.push({
            path: "/teacher/edit",
            query: { isStudent: false, data },
          });
        },
      },
    };
  },
};
</script>

<style scoped>
.teacher-list-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
.teacher-list-wrap {
  width: 90%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
.teacher-list {
  width: 100%;
  height: 92%;
}
</style>