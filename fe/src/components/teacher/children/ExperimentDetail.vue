<style lang="scss" scoped>
@import '@/styles/student';

.header {
  margin-bottom: 20px;

  .back .el-button {
    transition: $transition-all;

    &:hover {
      transform: translateX(-3px);
    }
  }
}

.el-row {
  display: flex;
  gap: 20px;
  @include responsive-layout;
}

.experiment {
  @include card-style;

  > div {
    display: flex;
    margin: 12px 0;
    align-items: center;

    .label {
      font-weight: bold;
      min-width: 120px;
      color: $text-primary;
    }

    .value {
      font-weight: bold;
      color: $highlight-color;
    }
  }

  .name {
    font-size: 24px;
    margin-bottom: 20px;
    color: $text-primary;
  }

  .teacherDocs, .uploadFile {
    margin-top: 20px;

    .list .item {
      @include card-style;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px 15px;
      margin-bottom: 10px;

      .origin-name {
        font-weight: bold;
        color: $highlight-color;
        flex: 1;
      }

      .el-button {
        margin-left: 10px;
        transition: $transition-all;

        &:hover {
          transform: scale(1.1);
        }
      }
    }
  }

  .uploadFile .label {
    margin-bottom: 15px;

    .el-button {
      padding: 10px 20px;
      font-size: 16px;
    }
  }
}

.rankList {
  @include card-style;
  height: fit-content;
  weight: 50%;

  .title {
    font-size: 20px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
    color: $text-primary;
    padding-bottom: 10px;
    border-bottom: 1px solid $border-color;
  }

  .list {
    max-height: 500px;
    overflow-y: auto;
    padding: 0 10px;

    .header, .item {
      display: flex;
      justify-content: space-between;
      padding: 12px 0;
      border-bottom: 1px dashed $border-color;

      > div {
        flex: 1;
        text-align: center;
      }
    }

    .header {
      font-weight: bold;
      color: $warning-color;
      position: sticky;
      top: 0;
      background: $card-bg;
    }

    .item {
      color: $rank-color;
      transition: $transition-all;

      &:hover {
        background-color: rgba($primary-color, 0.05);
      }
    }
  }
}

.deadline-warning {
  color: $danger-color;
  font-weight: bold;
  padding: 10px;
  background-color: rgba($danger-color, 0.1);
  border-radius: $border-radius-small;
  text-align: center;
}

:deep(.el-dialog) {
  border-radius: $border-radius-base;

  .el-upload-dragger {
    padding: 30px;
    border-radius: $border-radius-base;

    .el-icon--upload {
      font-size: 60px;
      color: $primary-color;
      margin-bottom: 15px;
    }

    .el-upload__text {
      font-size: 16px;

      em {
        color: $primary-color;
        font-style: normal;
      }
    }
  }

  .uploadFileName {
    margin-top: 15px;

    .name {
      color: $highlight-color;
      font-weight: bold;
      margin-top: 5px;
    }
  }
}

@media (max-width: 768px) {
  .experiment > div {
    flex-direction: column;
    align-items: flex-start;

    .label {
      margin-bottom: 5px;
    }
  }

  .rankList {
    width: 120%;
    margin-top: 10px;

    .list {
      height: 70%;
      max-height: 300px;
      width: 100%;
    }
  }
}
</style>
<template>
    <div class="header">
        <div class="back">
            <el-button type="warning" :icon="Back" size="small" @click="router.back()">返回</el-button>
        </div>
    </div>
    <el-row>
        <el-col :span="12">
            <div class="experiment">
                <div class="name">
                    <div class="label">
                        实验名:
                    </div>
                    <div class="value">
                        {{ experiment.name }}
                    </div>
                </div>
                <div class="description">
                    <div class="label">
                        实验描述:
                    </div>
                    <div class="value">
                        {{ experiment.description }}
                    </div>
                </div>
                <div class="judgeUrl">
                    <div class="label">评判接口地址</div>
                    <div class="value">{{ experiment.judgeUrl }}</div>
                </div>
                <div class="uploadCount">
                    <div class="label">学生总共可上传次数</div>
                    <div class="value">{{ experiment.totalUploadCount }}</div>
                </div>
                <div class="uploadFileType">
                    <div class="label">学生需要上传文件类型</div>
                    <div class="value">{{ experiment.uploadFileType }}</div>
                </div>
                <div class="uploadFile">
                    <div class="label">
                        <el-button type="primary" :icon="Plus" size="mini"
                            @click="uploadDocumentVisible = true">点击上传实验材料</el-button>
                    </div>
                    <div class="value">
                        <div class="list">
                            <div class="item" v-for="document in teacherDocuments">
                                <div class="origin-name">
                                    {{ document.originName }}
                                </div>
                                <el-button type="success" :icon="Download" circle size="mini"
                                    @click="getFile(document.saveName)"></el-button>
                                <el-button type="danger" :icon="Delete" circle size="mini"
                                    @click="deleteTeacherDocument(document.id)"></el-button>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="time">
                    <div class="label">创建时间</div>
                    <div class="value">{{ experiment.createdTime }}</div>
                </div>
                <div class="time">
                    <div class="label">截止时间</div>
                    <div class="value">{{ experiment.deadlineTime }}</div>
                </div>

            </div>
        </el-col>
        <div class="rankList">
          <div class="title">本次实验排行榜</div>
          <div class="list">
              <div class="header">
                  <div class="rank">排名</div>
                  <div class="name">姓名</div>
                  <div class="score">分数</div>
              </div>
              <div class="item" v-for="item in currentRankList" :key="item.name">
                  <div class="rank">{{ item.rank }}</div>
                  <div class="name">{{ item.name }}</div>
                  <div class="score">{{ item.score }}</div>
              </div>
          </div>
        </div>
    </el-row>
    <el-dialog v-model="uploadDocumentVisible" width="500" title="上传实验材料" :before-close="handleClose">
        <el-upload class="upload-demo" :on-change="handleFileChange" :show-file-list="false" :auto-upload="false" drag
            :multiple="false">
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
                拖动文件至此或者 <em>点击上传</em>
            </div>
            <template #tip>
                <div class="el-upload__tip">
                    上传本次实验的资料,供学生下载参考
                </div>
                <div class="uploadFileName" v-if="uploadDocumentFile">
                    <span class="label">当前选择的文件：</span><span class="name">{{ uploadDocumentFile.name }}</span>
                </div>
            </template>
        </el-upload>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="uploadDocumentVisible = false">取消</el-button>
                <el-button type="success" @click="handleUploadDocument" :disabled="!uploadDocumentFile">
                    上传
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import { getItemById as getExperimentByID } from "@api/experiment"
import { ref, onMounted } from "vue"
import { getDocuments, uploadDocument, deleteDocument } from "@api/document"
import { getFile } from "@api/files"
import { getUserInfo } from "@utils/access"
import { ElMessage } from "element-plus"
import {  Plus, Back, Delete, Download } from "@element-plus/icons-vue"
import { useRouter } from "vue-router"
import{ rankList } from "@api/experiment_student"
const router = useRouter()
const props = defineProps({
    id: Number
})

const teacherInfo = getUserInfo();

const experiment = ref({})

const teacherDocuments = ref([])
async function getExperiment() {
    const res = await getExperimentByID(props.id)
    if (res.data.code === 200) {
        experiment.value = res.data.data
    }
}

async function getTeacherDocuments() {
    const res = await getDocuments(props.id, teacherInfo.id, teacherInfo.role)
    if (res.data.code === 200) {
        teacherDocuments.value = res.data.data
    }
}


const deleteTeacherDocument = async (id) => {
    const res = await deleteDocument(id)
    if (res.data.code === 200) {
        teacherDocuments.value=teacherDocuments.value.filter(item=>item.id!==id)
        ElMessage.success("删除成功")
    }

}
const uploadDocumentFile = ref(null);
const uploadDocumentVisible = ref(false)

function handleFileChange(e) {
    uploadDocumentFile.value = e.raw;
}
async function handleUploadDocument() {
    console.log(`output->props.id`, props.id)
    console.log(`output-teacherInfo.id`, teacherInfo.id)
    console.log(`output-studentExcel.value`, uploadDocumentFile.value)
    const formData = new FormData()
    formData.append("experimentId", props.id)
    formData.append("userId", teacherInfo.id)
    formData.append("role", teacherInfo.role)
    formData.append("files", uploadDocumentFile.value)

    const res = await uploadDocument(formData)
    if (res.data.code === 200) {
        await getTeacherDocuments()
    }
    ElMessage.success(res.data.msg)
    uploadDocumentVisible.value = false
}

const importVisible = ref(false)


const currentRankList = ref([])
async function getCurrentRankList() {
    const res = await rankList(props.id);
    if (res.data.code === 200) {
        const rawList = res.data.data;// 原始数据
        // 计算排名
        const rankedList = calculateRank(rawList);
        // 更新列表
        currentRankList.value = rankedList;
    }
}
// 前端排名
function calculateRank(rankList) {
    let rank = 1; // 目前排名
    let prevScore = null; // 上一个分数
    let sameRankCount = 0; // 同分人数累计
    const result = [];

    rankList.forEach((item, index) => {
        if (item.score !== prevScore) {
            rank += sameRankCount; // 跳过相同分数的排名
            sameRankCount = 1;     // 重置同分计数
        } else {
            sameRankCount++; // 分数相同，同分计数+1
        }

        prevScore = item.score; // 更新上一分数

        // 添加排名字段
        result.push({
            ...item,
            rank,
        });
    });

    return result;
}

onMounted(async () => {
    await getExperiment();
    await getTeacherDocuments();
    await getCurrentRankList();
    document.title = "教师实验编辑_慧图工坊";
})
</script>

<style lang="less" scoped>

.experiment{
    >div{
        display: flex;
        margin: 10px 0;
        .label{
            font-weight: bold;
            margin-right: 20px;
            color: #333;
        }
        .value{
            font-weight: bold;
            color:#0a864c
        }
    }
    .name{
        font-size: 30px;
    }
    .uploadFile{
        display: block;
        .label{
            width: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 25px;
            color: #9f5608;
            span{margin-right: 20px;}
        }
        .list {
                margin-top: 5px;
                .item {
                    display: flex;
                    align-items: center;
                    margin-bottom: 10px;
                    .origin-name {
                        font-weight: bold;
                        color: #25b0f3;
                        margin-right: 20px;
                    }
                }
            }
    }

}

.rankList{
  width: 400px;
    .title{
        font-weight: bold;
        text-align: center;
        font-size: 30px;
        margin-bottom: 20px;
    }
    .list{
        height:400px;
        overflow-y: scroll;
    }
    .header,
    .item{
        width: 300px;
        margin: 20px auto;
        display: flex;
        justify-content: space-between;
        font-size: 20px;
        font-weight: bold;
    }
    .header{
        color: #9f5608;
    }
    .item{
        color:#008c8c
    }
}

</style>