<script setup lang="ts">
import {ref} from 'vue'

const text = ref("")
const show = ref<{
  keyboard: boolean
}>({
  keyboard: true
})
const type: 'uk' | 'us' = "us"
const vowels_uk = [
  ['iː', "i", 'ɪ', 'uː', "u", 'ʊ'],
  ['e', 'ɜː', 'ə', 'ʌ', 'ɔː'],
  ['æ', 'ɑː', 'ɒ'],
  ['eɪ', 'ɔɪ', 'aɪ', 'aʊ', 'əʊ', 'ɪə', 'ʊə', 'eə'],
  ['aɪə', 'aʊə'],
]
const vowels_us = [
  ['iː', "i", 'ɪ', 'uː', "u", 'ʊ'],
  ['e', 'ɜː', 'ə', 'ɚ', 'ʌ', 'ɔː'],
  ['æ', 'ɑː'],
  ['eɪ', 'ɔɪ', 'aɪ', 'aʊ', 'oʊ', 'ɪə', 'ʊə', 'eə'],
]
const vowels: string[][] = (function () {
  switch (type) {
    case "us": return vowels_us;
    case "uk": return vowels_uk;
    default: return vowels_us;
  }
})()
const obj = [
    vowels,
    [
      ["p", "t", "k", "f", "θ", "s", "ʃ", "tʃ"],
      ["b", "d", "ɡ", "v", "ð", "z", "ʒ", "dʒ"],
      ["m", "n", "ŋ", "h", "w", "r", "j", "l"],
      ["ts", "dz", "tr", "dr"],
      ["ˈ", "ˌ", ".", "del"],
    ]
]

const append = (str: string) => {
  text.value += str
}
const del = () => {
  text.value = text.value.substring(0, text.value.length - 1)
  const vibrate: Function = navigator.vibrate || navigator.webkitVibrate || navigator.mozVibrate || navigator.msVibrate;
  if(vibrate) {
      console.log("支持设备震动！");
      vibrate(1000)
  }
}

</script>
<template>
  <van-form>
    <van-cell-group title="发音">
      <van-field
             readonly
             v-model="text"
             label="音标(US)"
             placeholder="点此输入"
             @click="show.keyboard = true"
      />
      <van-field
             readonly
             v-model="text"
             label="音标(UK)"
             placeholder="点此输入"
             @click="show.keyboard = true"
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
      v-model:show="show.keyboard"
      position="bottom"
  >
      <div v-for="o in obj"
           class="grid grid-cols-8 gap-1 mb-3"
      >
        <template v-for="items in o">
          <template v-for="item in items">
            <button v-if="item === 'del' "
                    class="p-2 w-10 bg-gray-300 active:bg-gray-400 text-center rounded-lg"
                    @click="del"
            >
               <svg viewBox="0 -6 36 36">
                 <path d="M28 0a4 4 0 0 1 4 4v14a4 4 0 0 1-4 4H10.4a2 2 0 0 1-1.4-.6L1 13.1c-.6-.5-.9-1.3-.9-2 0-1 .3-1.7.9-2.2L9 .6a2 2 0 0 1 1.4-.6zm0 2H10.4l-8.2 8.3a1 1 0 0 0-.3.7c0 .3.1.5.3.7l8.2 8.4H28a2 2 0 0 0 2-2V4c0-1.1-.9-2-2-2zm-5 4a1 1 0 0 1 .7.3 1 1 0 0 1 0 1.4L20.4 11l3.3 3.3c.2.2.3.5.3.7 0 .3-.1.5-.3.7a1 1 0 0 1-.7.3 1 1 0 0 1-.7-.3L19 12.4l-3.4 3.3a1 1 0 0 1-.6.3 1 1 0 0 1-.7-.3 1 1 0 0 1-.3-.7c0-.2.1-.5.3-.7l3.3-3.3-3.3-3.3A1 1 0 0 1 14 7c0-.3.1-.5.3-.7A1 1 0 0 1 15 6a1 1 0 0 1 .6.3L19 9.6l3.3-3.3A1 1 0 0 1 23 6z"/>
               </svg>
            </button>
            <button v-else class="p-2 w-10 bg-gray-300 active:bg-gray-400 text-center rounded-lg"
                    @click="append(item)"
            >
              {{ item }}
            </button>
          </template>
        </template>
      </div>
  </van-popup>
</template>
