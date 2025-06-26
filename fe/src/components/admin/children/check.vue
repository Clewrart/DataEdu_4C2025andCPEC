<template>
  <div class="container">
    <div class="header">
      <el-select
          v-model="selectedExperiment"
          placeholder="选择实验"
          @change="handleExperimentChange"
          :loading="loading.experiments"
      >
        <el-option
            v-for="exp in experiments"
            :key="exp.id"
            :label="`${exp.name}`"
            :value="exp.id"
        />
      </el-select>
    </div>

    <div class="stats-container">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card class="stat-card">
            <div class="stat-title">选课人数</div>
            <div class="stat-value">{{ stats.enrolledCount || 0 }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <div class="stat-title">实验进度</div>
            <div class="stat-value">
              {{ stats.submittedCount || 0 }}/{{ stats.enrolledCount || 0 }}
              ({{ progressPercentage }}%)
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <div class="stat-title">平均分</div>
            <div class="stat-value">{{ stats.averageScore ? stats.averageScore.toFixed(1) : 0 }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <div class="stat-title">学生文档总数</div>
            <div class="stat-value">{{ stats.documentCount || 0 }}</div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="charts-container">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card>
            <div class="chart-title">教学反馈建议</div>
            <div v-if="aiFeedback" class="feedback-text">{{ aiFeedback }}</div>
            <div v-else class="empty-chart">暂无建议</div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <div class="chart-title">分数分布</div>
            <div
                v-if="hasScoreData"
                ref="scoreChart"
                class="chart"
            ></div>
            <div v-else class="empty-chart">暂无分数分布数据</div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="experiment-details">
      <el-card>
        <div class="details-header">
          <h3>实验详情</h3>
        </div>
        <div class="detail-item">
          <span class="detail-label">实验名称:</span>
          <span class="detail-value">{{ currentExperiment.name || '--' }}</span>
        </div>

        <div class="detail-item">
          <span class="detail-label">实验说明:</span>
          <span class="detail-value">{{ currentExperiment.description || '--' }}</span>
        </div>

        <div class="detail-item">
          <span class="detail-label">上传文件类型:</span>
          <span class="detail-value">{{ currentExperiment.uploadFileType || '--' }}</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, nextTick, watch } from 'vue'
import * as echarts from 'echarts'
import { ElMessage } from 'element-plus'
import {
  listExperimentsForCheck,
  getExperimentStats,
  getSubmissionTrend,
  getScoreDistribution,
  downloadMaterials
} from '@api/check'

// 数据定义
const experiments = ref([])
const selectedExperiment = ref(null)
const currentExperiment = ref({})

const loading = ref({
  experiments: false,
  stats: false,
  download: false
})

const stats = ref({
  enrolledCount: 0,
  submittedCount: 0,
  averageScore: 0,
  documentCount: 0,
  deadline: null
})

const scoreData = ref({
  excellent: 0,
  good: 0,
  medium: 0,
  pass: 0,
  fail: 0
})

const scoreChart = ref(null)

// 计算总人数
const totalScoreCount = computed(() => {
  const s = scoreData.value
  return s.excellent + s.good + s.medium + s.pass + s.fail
})

// 判断是否有有效分数数据
const hasScoreData = computed(() => totalScoreCount.value > 0)

// 计算进度百分比
const progressPercentage = computed(() => {
  if (!stats.value.enrolledCount) return 0
  return Math.round((stats.value.submittedCount / stats.value.enrolledCount) * 100)
})

// 计算教学反馈建议
const aiFeedback = computed(() => {
  if (!hasScoreData.value) return ''

  const s = scoreData.value
  const total = totalScoreCount.value
  const avg = stats.value.averageScore || 0

  const excellentRate = s.excellent / total
  const failRate = s.fail / total

  let suggestions = []

  if (avg < 60) {
    suggestions.push('本实验班级整体平均分低于60分，反映出学生对基础知识的掌握较为薄弱。建议教师在后续教学中系统性地梳理教材重点内容，设计更多专项训练，并定期进行小测验以巩固学习效果，同时可考虑建立学习小组互相帮扶。')
  } else if (avg >= 90) {
    suggestions.push('本实验班级平均分达到90分以上，表明当前教学方案取得了显著成效，大部分学生已熟练掌握课程内容。为保持学习动力并进一步提升能力，建议在维持现有教学质量的基础上，适当引入拓展性教学内容，设计更具挑战性的行业综合应用题')
  } else {
    suggestions.push('本实验班级平均分在60-90分之间，显示教学效果尚可但仍有提升空间。建议教师通过分析访谈，找出错误率较高的知识点，在课堂上进行针对性讲解，同时设计分层作业，对中等生开展专题强化训练，并建立错题本跟踪机制。')
  }

  if (failRate > 0.2) {
    suggestions.push('不及格比例较高，需关注学习困难学生，安排补习。')
  } else if (failRate > 0) {
    suggestions.push('部分学生成绩未达标，建议个别辅导。')
  }

  if (excellentRate < 0.1) {
    suggestions.push('优秀率不足10%，反映出拔尖学生培养有待加强。建议设立学习标兵评选机制，开展学科竞赛活动，为有潜力的学生提供拓展学习资源，同时改进课堂教学方式，增加探究性学习环节，激发学生的深层学习兴趣。')
  } else if (excellentRate > 0.3) {
    suggestions.push('优秀率超过30%，表明相当比例学生已很好掌握实验内容。为充分发挥这些学生的潜力，建议开设学科兴趣小组，布置研究性学习任务，推荐参加校级以上竞赛活动，并鼓励他们担任小老师帮助其他同学。')
  }

  return suggestions.join(' ')
})

// 生命周期挂载，初始化加载
onMounted(async () => {
  await loadExperiments()
  initScoreChart()
})

// 监听分数数据变化，刷新饼图
watch(scoreData, () => {
  updateScoreChart()
})

// 请求实验列表
async function loadExperiments() {
  loading.value.experiments = true
  try {
    const res = await listExperimentsForCheck()
    if (res.data?.code === 200) {
      experiments.value = res.data.data.list || []

      if (experiments.value.length > 0) {
        selectedExperiment.value = experiments.value[0].id
        await loadExperimentData(selectedExperiment.value)
      }
    } else {
      ElMessage.error(res.data?.msg || '获取实验列表失败')
    }
  } catch (error) {
    console.error('加载实验列表失败:', error)
    ElMessage.error('加载实验列表失败，请检查网络连接')
  } finally {
    loading.value.experiments = false
  }
}

// 请求实验详细数据和统计
async function loadExperimentData(experimentId) {
  loading.value.stats = true
  try {
    // 设置当前实验基本信息
    currentExperiment.value = experiments.value.find(e => e.id === experimentId) || {}

    // 请求统计数据
    const statsRes = await getExperimentStats(experimentId)
    if (statsRes.data?.code === 200) {
      stats.value = statsRes.data.data
    } else {
      ElMessage.error(statsRes.data?.msg || '获取统计数据失败')
    }

    // 请求分数分布数据
    const scoreRes = await getScoreDistribution(experimentId)
    if (scoreRes.data?.code === 200) {
      scoreData.value = scoreRes.data.data || {
        excellent: 0,
        good: 0,
        medium: 0,
        pass: 0,
        fail: 0
      }
    } else {
      ElMessage.warning(scoreRes.data?.msg || '获取分数分布失败')
      scoreData.value = { excellent: 0, good: 0, medium: 0, pass: 0, fail: 0 }
    }
  } catch (error) {
    console.error('加载实验数据失败:', error)
    ElMessage.error('加载实验数据失败')
  } finally {
    loading.value.stats = false
  }
}

// 初始化分数分布饼图
function initScoreChart() {
  nextTick(() => {
    if (!scoreChart.value) return
    const chart = echarts.init(scoreChart.value)
    chart.setOption({
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '5%',
        left: 'center'
      },
      series: [{
        name: '分数分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: []
      }]
    })
  })
}

// 更新分数分布饼图
function updateScoreChart() {
  nextTick(() => {
    if (!scoreChart.value) return
    const chart = echarts.getInstanceByDom(scoreChart.value)
    if (!chart) return

    const chartData = [
      { value: scoreData.value.excellent || 0, name: '优秀 (90-100)' },
      { value: scoreData.value.good || 0, name: '良好 (80-89)' },
      { value: scoreData.value.medium || 0, name: '中等 (70-79)' },
      { value: scoreData.value.pass || 0, name: '及格 (60-69)' },
      { value: scoreData.value.fail || 0, name: '不及格 (<60)' }
    ]

    chart.setOption({
      series: [{
        data: chartData
      }]
    })
  })
}

// 选择实验变化处理
async function handleExperimentChange(experimentId) {
  await loadExperimentData(experimentId)
}

</script>

<style scoped>
.container {
  padding: 20px;
}

.header {
  margin-bottom: 30px;
}

.stats-container {
  margin-bottom: 30px;
}

.stat-card {
  text-align: center;
  height: 120px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.stat-title {
  font-size: 16px;
  color: #666;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.charts-container {
  margin-bottom: 30px;
}
.feedback-text {
  padding: 20px;
  font-size: 16px;
  line-height: 1.8;
  color: #333;
}

.chart {
  height: 300px;
  width: 100%;
}

.empty-chart {
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}

.chart-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
}

.experiment-details {
  margin-top: 30px;
}

.details-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.detail-item {
  margin-bottom: 15px;
  display: flex;
}

.detail-label {
  font-weight: bold;
  min-width: 120px;
  color: #666;
}

.detail-value {
  color: #333;
}
</style>
