<script setup lang="ts">
import Recorder from 'js-audio-recorder'
import {ref} from 'vue'

type StatusType = "not-record" | "recording" | "completed";
const status = ref<StatusType>('not-record');

const recorder = new Recorder({
  sampleRate: 48000
});
// noinspection SpellCheckingInspection
recorder.onplay = recorder.onresumeplay = () => {
  show.value.play = true
}
// noinspection SpellCheckingInspection
recorder.onpauseplay = recorder.onstopplay = recorder.onplayend = () => {
  show.value.play = false
}

let oCanvas: HTMLCanvasElement
let ctx: CanvasRenderingContext2D

const start = () => {
  recorder.start().then(() => {
    status.value = "recording"
  }, (err) => {
    console.log(err)
  })
  oCanvas = document.getElementById("canvas") as HTMLCanvasElement;
  ctx = oCanvas.getContext("2d")!!;
  draw()
}
const stop = () => {
  recorder.stop()
  status.value = "completed"
}

const show = ref<{
  bottom: boolean
  play: boolean
}>({
  bottom: true,
  play: false
})
const play = () => {
  recorder.play()
}
const stopPlay = () => {
  recorder.stopPlay()
}

const draw = () => {
  requestAnimationFrame(draw);
  // 实时获取音频大小数据
  let dataArray = recorder.getRecordAnalyseData()
  let bufferLength = Math.min(dataArray.length);

  // 填充背景色
  ctx.fillStyle = 'rgb(255,255,255)';
  ctx.fillRect(0, 0, oCanvas.width, oCanvas.height);

  // 设定波形绘制颜色
  ctx.lineWidth = 1;
  ctx.strokeStyle = 'rgb(0, 0, 0)';

  ctx.beginPath();

  // 一个点占多少位置，共有bufferLength个点要绘制
  let sliceWidth = oCanvas.width / bufferLength
  // 绘制点的x轴位置
  let x = 0
  for (let i = 0; i < bufferLength; i++) {
    const v = dataArray[i] / 128;
    const y = v * oCanvas.height / 2;
    if (i === 0) {
      // 第一个点
      ctx.moveTo(x, y);
    } else {
      // 剩余的点
      ctx.lineTo(x, y);
    }
    // 依次平移，绘制所有点
    x += sliceWidth;
  }

  ctx.lineTo(oCanvas.width, oCanvas.height / 2);
  ctx.stroke();
}

</script>
<template>
  <van-button @click="show.bottom = true">
    按钮
  </van-button>
  <van-button @click="play">
    播放
  </van-button>
  <van-popup
      class="flex items-center justify-center"
      v-model:show="show.bottom"
      position="bottom"
      :style="{ height: '40%' }"
      @close="stop"
  >
    <div class="flex">
      <van-icon
          v-show="status === 'not-record'"
          name="/assets/icon/microphone.svg"
          size="36px"
          @click="start"
      />
      <canvas id="canvas"
              class="cursor-pointer"
              v-show="status === 'recording' "
              style="width: 100%; height: 30%"
              @click="stop"
      />
      <div
          class="flex w-36 justify-around"
          v-show="status === 'completed'"
      >
        <van-icon
            class="cursor-pointer"
            name="/assets/icon/redo.svg"
            size="36px"
            @click="start"
        />
        <van-icon
            class="cursor-pointer"
            v-show="show.play === false"
            name="/assets/icon/play.svg"
            size="36px"
            @click="play"
        />
        <van-icon
            class="cursor-pointer"
            v-show="show.play === true"
            name="/assets/icon/stop_play.svg"
            size="36px"
            @click="stopPlay"
        />
      </div>
    </div>
  </van-popup>

</template>
