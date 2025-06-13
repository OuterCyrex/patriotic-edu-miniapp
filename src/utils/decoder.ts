export function ArrayBuffer2Utf8(buffer: ArrayBuffer): string {
  const bytes = new Uint8Array(buffer)
  let out = '', i = 0, len = bytes.length

  while (i < len) {
    const c = bytes[i++]
    if (c >> 7 === 0) {
      out += String.fromCharCode(c)
    } else if (c >> 5 === 0b110) {
      const c2 = bytes[i++]
      out += String.fromCharCode(((c & 0x1F) << 6) | (c2 & 0x3F))
    } else if (c >> 4 === 0b1110) {
      const c2 = bytes[i++]
      const c3 = bytes[i++]
      out += String.fromCharCode(((c & 0x0F) << 12) | ((c2 & 0x3F) << 6) | (c3 & 0x3F))
    }
  }

  return out
}

