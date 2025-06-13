export interface KnowledgeItem {
  id: number
  question: string
  optionA: string
  optionB: string
  optionC: string
  optionD: string
  difficulty: number
  done: number
  correct: boolean
  choice: number
  answer: number
  explanation: string
  recordId: number
}

export interface SubmitKnowledgeList {
  list: Array<SubmitKnowledgeItem>
  ac: number
  wa: number
  comment: string
  stars: number
}

export interface SubmitKnowledgeItem {
  result: boolean
  explanation: string
  recordId: number
  questionId: number
}

export interface ScenarioItem {
  id: number
  scenario: string
  question: string
  optionA: string
  optionB: string
  optionC: string
  done: number
  correct: boolean
  choice: number
  answer: number
  legalBasis: string
  solution: string
}
