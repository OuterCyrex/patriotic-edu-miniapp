export interface ArticleCardProps {
  cover: string
  title: string
  summary: string
  views: number | string
  onclick?: () => void
}

export class ArticleCardData implements ArticleCardProps {
  cover: string
  title: string
  summary: string
  views: number | string
  onclick?: () => void

  constructor(cover: string, title: string, summary: string, views: number | string, onclick?: () => void) {
    this.cover = cover
    this.title = title
    this.summary = summary
    this.views = views
    this.onclick = onclick ? onclick : () => {}
  }
}
