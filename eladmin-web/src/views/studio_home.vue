<template>
  <div class="chart-container">
    <el-card class="chart-card">
      <div ref="dailyChart" class="echarts-chart" />
    </el-card>
  </div>
</template>

<script>
import echarts from 'echarts'
import axios from 'axios'

export default {
  mounted() {
    this.fetchDailyVisitCount()
    this.fetchMonthlyVisitCount()
  },
  methods: {
    fetchDailyVisitCount() {
      axios.get('/api/page-visit/dailyVisitCount')
        .then(response => {
          const data = response.data
          this.renderDailyVisitChart(data)
        })
        .catch(error => {
          console.error('Error fetching daily visit count:', error)
        })
    },
    fetchMonthlyVisitCount() {
      axios.get('/api/page-visit/monthlyVisitCount')
        .then(response => {
          const data = response.data
          this.renderMonthlyVisitChart(data)
        })
        .catch(error => {
          console.error('Error fetching monthly visit count:', error)
        })
    },
    renderDailyVisitChart(data) {
      const chart = echarts.init(this.$refs.dailyChart)
      const dates = Object.keys(data)
      const counts = Object.values(data)

      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' }
        },
        title: {
          text: '访问量统计'
        },
        xAxis: {
          type: 'category',
          data: dates
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: counts,
          type: 'line',
          smooth: true
        }]
      }

      chart.setOption(option)
    },
    renderMonthlyVisitChart(data) {
      const chart = echarts.init(this.$refs.monthlyChart)
      const months = Object.keys(data).map(month => new Date(month).toLocaleString('default', { month: 'long' }))
      const counts = Object.values(data)

      const option = {
        title: {
          text: 'Monthly Visit Count'
        },
        xAxis: {
          type: 'category',
          data: months
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: counts,
          type: 'line',
          smooth: true
        }]
      }

      chart.setOption(option)
    }
  }
}
</script>

<style>
.chart-container {
  display: flex;
  justify-content: center;
}

.chart-card {
  margin-top: 30px;
  width: 100%;
  max-width: 800px;
  margin-bottom: 20px;
}

.echarts-chart {
  width: 100%;
  height: 400px;
}
</style>
