<script setup lang="ts">

type Type = 'uk' | 'us'

const props = defineProps<{
  type: Type
}>();
const modelValue = defineModel<string>()

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
  switch (props.type) {
    case "us": return vowels_us;
    case "uk": return vowels_uk;
    default: return vowels_us;
  }
})()
const pos = [
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
  modelValue.value += str
}
const del = () => {
  modelValue.value = modelValue.value?.substring(0, modelValue.value.length - 1)
}

</script>
<template>
  <div v-for="group in pos"
       class="grid grid-cols-8 gap-1 mb-3"
  >
    <template v-for="items in group">
      <template v-for="item in items">
        <button v-if="item === 'del' "
                class="button p-0"
                @click="del"
        >
           <van-icon
              name="/assets/icon/del.svg"
              size="20px"
          />
        </button>
        <button v-else
                class="button"
                @click="append(item)"
        >
          {{ item }}
        </button>
      </template>
    </template>
  </div>
</template>
<style>
.button {
  @apply p-2 w-10 bg-gray-300 active:bg-gray-400 text-center rounded-lg;
}
</style>
