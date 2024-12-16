<template>
    <div class="demo-datetime-picker">

        <div class="block">
            <span class="demonstration">{{ label }}:</span>
            <el-date-picker v-model="value" @change="handleChange" :default-time="defaultTime" type="datetime"
                placeholder="请选择日期和时间" :shortcuts="shortcuts" />
        </div>

    </div>
</template>

<script setup>
import { ref } from 'vue'
import { formatTimeJSDate } from "@utils/time"

const emits = defineEmits(['change'])


const props = defineProps({
    label: {
        type: String,
        default: '日期时间选择器'
    },
    time: {
        type: String,
        default: ''
    }
})

const value = ref(props.time)

function handleChange(datetime) {
    emits('change', formatTimeJSDate(datetime,"YYYY-MM-DD HH:mm:ss"))

}

const defaultTime = new Date()



const shortcuts = [
    {
        text: '一小时后',
        value: () => {
            const date = new Date()
            date.setHours(date.getHours() + 1)
            return date
        },
    },
    {
        text: '两小时后',
        value: () => {
            const date = new Date()
            date.setHours(date.getHours() + 2)
            return date
        },
    },
    {
        text: '今天',
        value: () => {
            const date = new Date()
            date.setHours(23)
            date.setMinutes(59)
            date.setSeconds(59)
            return date
        },
    },
    {
        text: '明天',
        value: () => {
            const date = new Date()
            date.setDate(date.getDate() + 1)
            return date
        },
    },
    {
        text: '后天',
        value: () => {
            const date = new Date()
            date.setDate(date.getDate() + 2)
            return date
        },
    },
    {
        text: '三天后',
        value: () => {
            const date = new Date()
            date.setDate(date.getDate() + 3)
            return date
        },
    },
    {
        text: '一周后',
        value: () => {
            const date = new Date()
            date.setDate(date.getDate() + 7)
            return date
        },
    },
]
</script>
<style scoped>
.demo-datetime-picker {
    display: flex;
    width: 100%;
    padding: 0;
    flex-wrap: wrap;
}

.demo-datetime-picker .block {
    display: flex;
    align-items: center;
    text-align: center;
    border-right: solid 1px var(--el-border-color);
    flex: 1;
}

.demo-datetime-picker .block:last-child {
    border-right: none;
}

.demo-datetime-picker .demonstration {
    display: block;
    color: var(--el-text-color-secondary);
    margin-right: 10px;
}
</style>