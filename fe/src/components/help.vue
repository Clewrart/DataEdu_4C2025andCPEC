<template>
  <div class="app">
    <div class="agreement_picture">
      <div class="pdf">
        <iframe :src="src" frameborder="0"></iframe>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PageOne",
  data() {
    return {
      src: "https://www.clewrart.top/content/uploadfile/202503/af481742539557.pdf",
    };
  },
  mounted() {
    document.title = "关于平台";
  }
}
</script>

<style scoped>
.app, .agreement_picture {
  width: 99vw;
  height: 99vh;
  display: flex;
  align-items: center; 
  justify-content: center;
}

.pdf {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 99vw; 
  height: 99vh; 
}

iframe {
  width: 99%;
  height: 99%;
  border: none; 
}
</style>
