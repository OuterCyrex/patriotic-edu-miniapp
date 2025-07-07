import { defineConfig, type UserConfigExport } from '@tarojs/cli'
import TsconfigPathsPlugin from 'tsconfig-paths-webpack-plugin'
import ComponentsPlugin from 'unplugin-vue-components/webpack'
import NutUIResolver from '@nutui/auto-import-resolver'
import devConfig from './dev'
import prodConfig from './prod'

// https://taro-docs.jd.com/docs/next/config#defineconfig-辅助函数
export default defineConfig<'webpack5'>(async (merge, _) => {
  const baseConfig: UserConfigExport<'webpack5'> = {
    projectName: 'patriotic-edu-miniapp',
    date: '2025-5-31',
    designWidth (input: any) {
      if (input?.file?.replace(/\\+/g, '/').indexOf('@nutui') > -1) {
        return 375
      }
      return 750
    },
    deviceRatio: {
      640: 2.34 / 2,
      750: 1,
      375: 2,
      828: 1.81 / 2
    },
    sourceRoot: 'src',
    outputRoot: 'dist',
    plugins: [
      '@tarojs/plugin-html',
    ],
    defineConstants: {
    },
    copy: {
      patterns: [
      ],
      options: {
      }
    },
    framework: 'vue3',
    compiler: {
      type: 'webpack5',
      prebundle: {
        exclude: ['@nutui/nutui-taro', '@nutui/icons-vue-taro']
      }
    },
    sass: {
      // 默认京东 APP 10.0主题 > @import "@nutui/nutui-taro/dist/styles/variables.scss";
      // 京东科技主题 > @import "@nutui/nutui-taro/dist/styles/variables-jdt.scss";
      // 京东B商城主题 > @import "@nutui/nutui-taro/dist/styles/variables-jdb.scss";
      // 京东企业业务主题 > @import "@nutui/nutui-taro/dist/styles/variables-jddkh.scss";
      data: `@import "@nutui/nutui-taro/dist/styles/variables.scss";`
    },
    cache: {
      enable: false
    },
    mini: {
      postcss: {
        pxtransform: {
          enable: true,
          config: {

          }
        },
        cssModules: {
          enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
          config: {
            namingPattern: 'module', // 转换模式，取值为 global/module
            generateScopedName: '[name]__[local]___[hash:base64:5]'
          }
        }
      },
      webpackChain(chain) {
        chain.resolve.plugin('tsconfig-paths').use(TsconfigPathsPlugin)
        chain.plugin('unplugin-vue-components').use(ComponentsPlugin({
          resolvers: [NutUIResolver({taro: true})]
        }))
      }
    },
    h5: {
      publicPath: '/',
      staticDirectory: 'static',
      output: {
        filename: 'js/[name].[hash:8].js',
        chunkFilename: 'js/[name].[chunkhash:8].js'
      },
      miniCssExtractPluginOption: {
        ignoreOrder: true,
        filename: 'css/[name].[hash].css',
        chunkFilename: 'css/[name].[chunkhash].css'
      },
      postcss: {
        autoprefixer: {
          enable: true,
          config: {}
        },
        cssModules: {
          enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
          config: {
            namingPattern: 'module', // 转换模式，取值为 global/module
            generateScopedName: '[name]__[local]___[hash:base64:5]'
          }
        }
      },
      webpackChain(chain) {
        chain.resolve.plugin('tsconfig-paths').use(TsconfigPathsPlugin)
        chain.plugin('unplugin-vue-components').use(ComponentsPlugin({
          resolvers: [NutUIResolver({taro: true})]
        }))
      },
    },
    rn: {
      appName: 'taroDemo',
      postcss: {
        cssModules: {
          enable: false,
        }
      }
    }
  }


  if (process.env.NODE_ENV === 'development') {
    // 本地开发构建配置（不混淆压缩）
    return merge({}, baseConfig, devConfig)
  }
  // 生产构建配置（默认开启压缩混淆等）
  return merge({}, baseConfig, prodConfig)
})
