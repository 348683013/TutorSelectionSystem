<template>
  <div>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage1"
      :page-size="8"
      layout="total, prev, pager, next, jumper"
      :total="totalSize"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "Paganation",
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.$emit('getCurrentPage',val)
    },
  },
  data() {
    return {
      currentPage1: 0,
    };
  },
  props:['totalSize']
};
</script>

<style scoped>

</style>