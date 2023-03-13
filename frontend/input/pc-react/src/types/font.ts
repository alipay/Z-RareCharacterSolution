export interface FontLibrary {
  charId?: string;
  unicodeChar: string;
  unicodeCodePoint: string;
  unicodeFont: string | null;
  pinYinChars: string[];
  splitChars: string[];
  weight?: number | null;
  type?: string;
  extInfo?: string | null;
  sort?: number;
}

export type FontLibraryArray = FontLibrary[];
