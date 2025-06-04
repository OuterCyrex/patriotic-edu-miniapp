import { createApp } from 'vue'
import { Row, Col } from '@nutui/nutui-taro'
import "@nutui/nutui-taro/dist/style.css";


const App = createApp({
  onShow () {},
})
App.use(Row)
App.use(Col)
export default App
