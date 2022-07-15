<template>
  <div class="process-content">
    <div class="process-line">
      <el-steps :active="2" align-center>
        <el-step
          title="步骤1"
          description="这是一段很长很长很长的描述性文字"
          status="success"
        ></el-step>
        <el-step
          title="步骤2"
          description="这是一段很长很长很长的描述性文字"
          status="wait"
        ></el-step>
        <el-step
          title="步骤3"
          description="这是一段很长很长很长的描述性文字"
          status="wait"
        ></el-step>
        <el-step
          title="步骤4"
          description="这是一段很长很长很长的描述性文字"
        ></el-step>
      </el-steps>
    </div>
    <div class="process-introduction">
      <div class="box1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>步骤一</span>
            <el-button style="float: right; padding: 3px 0;color:green;" type="text"
              >GO</el-button
            >
          </div>
          <div v-for="o in 4" :key="o" class="text item">
            {{ "列表内容 " + o }}
          </div>
        </el-card>
      </div>
      <div class="box1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>步骤二</span>
            <el-button style="float: right; padding: 3px 0;color:green" type="text"
              >GO</el-button
            >
          </div>
          <div v-for="o in 4" :key="o" class="text item">
            {{ "列表内容 " + o }}
          </div>
        </el-card>
      </div>
      <div class="box1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>步骤三</span>
            <el-button style="float: right; padding: 3px 0;color:green" type="text"
              >GO</el-button
            >
          </div>
          <div v-for="o in 4" :key="o" class="text item">
            {{ "列表内容 " + o }}
          </div>
        </el-card>
      </div>
      <div class="box1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>步骤四</span>
            <el-button style="float: right; padding: 3px 0;color:green" type="text"
              >GO</el-button
            >
          </div>
          <div v-for="o in 4" :key="o" class="text item">
            {{ "列表内容 " + o }}
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Process",
};
</script>

<style scoped>
.process-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.process-introduction {
  display: grid;
  grid-template-columns: 600px 600px;
  gap: 100px 300px;
  margin-top: 80px;
}
.box1 {
  width: 600px;
  height: 200px;
  /* border: 1px solid; */
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 100%;
  height: 100%;
}
</style>