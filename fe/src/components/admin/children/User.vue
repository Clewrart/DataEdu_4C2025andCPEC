<template>
    <div class="head">
        <el-input type="text" v-model="keyword" style="margin-right:15px;"> </el-input>
            <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
            <!-- <el-button type="primary" :icon="Plus" @click="addCallBack">添加</el-button> -->

    </div>
    <el-table :data="tableData" :scrollbar-always-on="true" style="width: 100%">
        <el-table-column prop="id" label="ID" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="role" label="角色" :formatter="formatRole" />
        <el-table-column prop="createdTime" label="创建时间" />
        <el-table-column label="操作" fixed="right" width="100px">
            <template #default="scope">
                <el-button type="primary" :icon="Edit" @click="editCallBack(scope.row)" style="width: 87px;">修改密码</el-button>
                <!-- <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#ff6c37"
                    title="确定删除吗?" @confirm="handleDelete(scope.row.id)" @cancel="cancelEvent">
                    <template #reference>
                        <el-button type="danger" :icon="Delete" :disabled="true">删除</el-button>
                    </template>
                </el-popconfirm> -->

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
                            <el-option v-for="size in pageSizes" :key="size" :label="size + '/页'" :value="size" />
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
                            <el-input v-model.number="currentPage" @change="handlePaginationChange"
                                style="width: 50px;" />
                        </div>
                    </div>
                </div>
            </template>
        </el-pagination>
    </div>


    <el-dialog v-model="editVisible" width="350" title="修改" :before-close="handleClose">
        <el-input type="text" v-model="editForm.username" placeholder="" disabled />
        <el-input type="password" v-model="editForm.password" placeholder="密码,不填则不会修改" />
        <!-- <el-select v-model="editForm.role" placeholder="Select" style="width: 240px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select> -->
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="handleEdit">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="addVisible" width="350" title="添加" :before-close="handleClose">

        <el-input type="text" v-model="addForm.username" placeholder="" />
        <el-input type="password" v-model="addForm.password" placeholder="" />
        <el-select v-model="addForm.role" placeholder="Select" style="width: 240px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="handleAdd">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
    <el-dialog v-model="imageListVisible" width="350" title="添加" :before-close="handleClose">
        <div class="item" v-for="item in imageList" :key="item">
            <el-image :src="item" style="width: 100px; height: 100px" />
        </div>
    </el-dialog>

</template>

<script setup>
import { ref, onMounted } from "vue";
import { listItem, deleteItem, updateItem, addItem } from "@api/user";
import { ElMessage } from "element-plus";
import { Search,Edit } from "@element-plus/icons-vue"
import { shallowCopyObjectWithEmptyValues } from "@utils/shallowCopyObjectWithEmptyValues";
const tableData = ref([]);

const keyword = ref("");

const currentPage = ref(1);
const currentSize = ref(5);
const pageSizes = [5, 10, 20, 50]
const total = ref(0)

const options = [
    {
        value: 'admin',
        label: '管理员'
    }
]

function formatRole(row, column, cellValue) {
    if (cellValue === 'admin') {
        return '管理员';
    } else if (cellValue === 'student') {
        return '学生';
    } else if (cellValue === 'teacher') {
        return '教师';
    }
}





function getStudentList() {
    listItem(keyword.value, currentPage.value, currentSize.value).then((res) => {
        if (res.data.code === 200) {
            tableData.value = res.data.data.list;
            total.value = res.data.data.total
        }
    });

}


function handlePaginationChange() {
    getStudentList();
}

onMounted(async () => {
    await getStudentList();
    document.title = "用户管理_慧图工坊";
});

const editVisible = ref(false);
const addVisible = ref(false);
const editForm = ref({

})

const addForm = ref({

})

function editCallBack(row) {
    editForm.value = Object.assign({}, row)
    if (editForm.value.password) {
        editForm.value.password=""
    }
    editVisible.value = true
}



function handleSearch() {
    getStudentList();
}

function handleEdit() {
    const formData = new FormData();
    for (const key of Object.keys(editForm.value)) {
        if (editForm.value[key]) {
            formData.append(key, editForm.value[key])
        }
    }


    updateItem(formData).then((res) => {
        if (res.data.code === 200) {
            getStudentList();
            ElMessage({ message: "修改成功", type: "success" })
            editVisible.value = false
        }
    });
}
function handleAdd() {
    const formData = new FormData();
    for (const key of Object.keys(addForm.value)) {
        if (addForm.value[key]) {
            formData.append(key, addForm.value[key]);
        }
    }

    addItem(formData).then((res) => {
        if (res.data.code === 200) {
            ElMessage({ message: "添加成功", type: "success" })
            addVisible.value = false
            getStudentList();
        }
    });

}


function handleClose(done) {
    done();
}


const imageList = ref([])



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
