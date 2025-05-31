export interface ButtonCardProps {
  title: string
  subtitle: string
  icon: string
  onClick?: () => void
}

export class ButtonCardData implements ButtonCardProps {
  public title: string
  public subtitle: string
  public icon: string
  public onClick: () => void

  constructor(title: string, subtitle: string, icon: string, onClick?: () => void) {
    this.title = title
    this.subtitle = subtitle
    this.icon = icon
    this.onClick = onClick ? onClick : () => {}
  }
}
