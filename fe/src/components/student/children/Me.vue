<template>
        <div class="head">
                <el-input type="text" v-model="keyword" style="margin-right:15px;"> </el-input>
                        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        </div>
        <el-table :data="tableDataPagedList" :scrollbar-always-on="true" style="width: 100%">
                <el-table-column prop="id" label="ID" width="100px" />
                <el-table-column prop="name" label="实验名" width="300px" />
                <el-table-column prop="description" label="实验说明" />
                <el-table-column label="操作" fixed="right" width="100px">
                        <template #default="scope">
                                <el-button type="primary" :icon="DArrowRight" @click="toDetail(scope.row.id)" style="width: 87px;">详情</el-button>
                        </template>
                </el-table-column>
        </el-table>
        <div class="pager">
                <el-pagination v-model:current-page="currentPage" v-model:page-size="currentSize"
                        layout=" prev, pager, next,slot" @current-change="handlePaginationChange" :total="total">
                        <template #default>
                                <div class="slot">
                                        <div class="sizes">
                                                <el-select v-model="currentSize" placeholder="页数" size="normal"
                                                        @change="handlePaginationChange">
                                                        <el-option v-for="size in pageSizes" :key="size"
                                                                :label="size + '/页'" :value="size" />
                                                </el-select>
                                        </div>
                                        <div class="total">
                                                <div class="label">
                                                        总数 :
                                                </div>
                                                <div class="value">
                                                        {{ total }}
                                                </div>
                                        </div>
                                        <div class="jumper">
                                                <div class="label">
                                                        跳转至
                                                </div>
                                                <div class="input">
                                                        <el-input v-model.number="currentPage"
                                                                @change="handlePaginationChange" style="width: 50px;" />
                                                </div>
                                        </div>
                                </div>
                        </template>
                </el-pagination>
        </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { listItemByStudentId as listItem } from "@api/experiment";
import { ElMessage } from "element-plus";
import { Search, DArrowRight } from "@element-plus/icons-vue"
import { getUserInfo } from "@utils/access"
import { computed } from "vue"
import { useRouter } from "vue-router"
const router=useRouter()

const studentInfo = getUserInfo()

const tableData = ref([]);

const keyword = ref("");

const currentPage = ref(1);
const currentSize = ref(3);
const pageSizes = [3, 5, 10, 20, 50]
const total = ref(0)

async function getMyExperiments() {
        const res = await listItem(studentInfo.id, keyword.value)
        if (res.data.code === 200) {
                tableData.value = res.data.data.list;
                total.value = res.data.data.total;
        } else {
                ElMessage.error(res.data.msg);
        }
}

function handleSearch() {
        getMyExperiments()
}

const tableDataPagedList = computed(() => {
        return tableData.value.slice((currentPage.value - 1) * currentSize.value, currentPage.value * currentSize.value)
})

function toDetail(id) {
        router.push(`/student/detail/${id}`)
}

onMounted(() => {
        getMyExperiments();
        document.title = "我的已选实验_机器学习与图像处理综合教学实验平台";
})

</script>

<style lang="less" scoped>
.head {
    width: 50%;
    display: flex;
    justify-content: space-between;
    margin-left: 30px;
}

.pager {
    margin-top: 10px;
    display: flex;
    justify-content: center;

    .slot {
        display: flex;
        justify-content: space-around;

        .sizes {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 90px;
            margin-left: 10px;
        }

        .total {
            width: 50px;
            display: flex;
            padding: 10px;
            align-items: center;

            .label {
                padding: 0 5px;
            }

            .value {
                padding: 0 5px;
                color: #008c8c;
            }
        }

        .jumper {
            display: flex;
            padding: 5px;
            align-items: center;

            .label {
                padding: 5px;
            }

            .input {
                padding: 5px;
            }
        }
    }
}

.list {
    display: flex;
    flex-direction: column;

    .item {
        display: flex;
        justify-content: space-between;
        padding: 10px;

        .label {
            width: 80px;
            font-weight: bold;
            color: #999;
        }

        .value {
            width: 400px;
            font-weight: bold;
        }
    }
}

@media (max-width: 768px) {
    .head{
        width: 90%;
    }
    .content{
        width: 90%;
        margin-left: 0px;
    }
    .head el-input{
        width: 100%;
    }
}
</style>
