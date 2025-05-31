import { createApp } from 'vue'
import { createUI } from 'taro-ui-vue3'

// 引用全部组件样式
import 'taro-ui-vue3/dist/style/index.scss'

const App = createApp({
  onShow() { },
})

// 引用全部组件
const tuv3 = createUI()
App.use(tuv3)

export default App
