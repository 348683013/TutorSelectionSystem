<template>
  <div class="student-list-wrapper">
    <div class="student-list-wrap">
      <div class="student-list">
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
  name: "Student",
  methods: {
    deleteRow(index, rows) {
      rows.splice(index, 1);
    },
  },
  components: {
    Manage,
  },
  data() {
    return {
      tableData: [
        {
          name: "张鑫南",
          number: "s210231264",
          class: "生信二班",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "已选取",
          address: "陕西省渭南市临渭区光电高层8楼b2",
        },
        {
          name: "张鑫南",
          number: "s210231265",
          class: "生信二班",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "已选取",
          address: "陕西省渭南市临渭区光电高层8楼b2",
        },
        {
          name: "张鑫南",
          number: "s210231264",
          class: "生信二班",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "已选取",
          address: "陕西省渭南市临渭区光电高层8楼b2",
        },
        {
          name: "张鑫南",
          number: "s210231264",
          class: "生信二班",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "已选取",
          address: "陕西省渭南市临渭区光电高层8楼b2",
        },
        {
          name: "张鑫南",
          number: "s210231264",
          class: "生信二班",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "已选取",
          address: "陕西省渭南市临渭区光电高层8楼b2",
        },
        {
          name: "张鑫南",
          number: "s210231264",
          class: "生信二班",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "已选取",
          address: "陕西省渭南市临渭区光电高层8楼b2",
        },
        {
          name: "张鑫南",
          number: "s210231264",
          class: "生信二班",
          phone: "18892133830",
          email: "1016217659@qq.com",
          status: "已选取",
          address: "陕西省渭南市临渭区光电高层8楼b2",
        },
      ],
      attr: {
        name: "姓名",
        number: "学号",
        class: "班级",
        phone: "电话",
        email: "邮箱",
        status: "选取状态",
        address: "联系地址",
        add: "添加学生",
        addEvent: () => {
          this.$router.push({
            path: "/student/edit",
            query: {
              isStudent: true,
              data: {
                name: "",
                number: "",
                class: "",
                phone: "",
                email: "",
                address: "",
                status: "",
              },
            },
          });
        },
        edit: (data) => {
          this.$router.push({
            path: "/student/edit",
            query: { isStudent: true, data },
          });
        },
      },
    };
  },
};
</script>

<style scoped>
.student-list-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
.student-list-wrap {
  width: 90%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
.student-list {
  width: 100%;
  height: 92%;
}
</style>