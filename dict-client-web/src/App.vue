<script setup lang="ts">
import IpaKeyBoard from "./components/IpaKeyBoard.vue";
import {ref} from "vue";

const show = ref<{
  usIpaKeyboard: boolean,
  ukIpaKeyboard: boolean,
}>({
  usIpaKeyboard: false,
  ukIpaKeyboard: false,
})

const params = ref<{
  us_api: string,
  uk_api: string,
}>({
  us_api: "",
  uk_api: "",
})

</script>
<template>
  <van-form>
    <van-cell-group title="发音">
      <van-field
             readonly
             v-model="params.us_api"
             label="音标(US)"
             placeholder="点此输入"
             @click="show.usIpaKeyboard = true"
      />
      <van-field
             readonly
             v-model="params.uk_api"
             label="音标(UK)"
             placeholder="点此输入"
             @click="show.ukIpaKeyboard = true"
      />
    </van-cell-group>
    <van-cell-group title="拼写">
      <van-field
          label="包含"
          placeholder="单词拼写包含"
      />
      <van-field
          label="正则"
          placeholder="正则表达式"
      />
    </van-cell-group>
    <van-cell-group title="构词">
      <van-field
          label="前缀"
          placeholder="单词前缀"
      />
      <van-field
          label="后缀"
          placeholder="单词后缀"
      />
      <van-field
          label="词根"
          placeholder="单词词根"
      />
    </van-cell-group>
  </van-form>

  <van-popup
      class="p-3"
      v-model:show="show.usIpaKeyboard"
      position="bottom"
  >
    <ipa-key-board type="us" v-model="params.us_api"/>
  </van-popup>
  <van-popup
      class="p-3"
      v-model:show="show.ukIpaKeyboard"
      position="bottom"
  >
    <ipa-key-board type="uk" v-model="params.uk_api"/>
  </van-popup>
</template>
