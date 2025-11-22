export function sortBy<T>(arr: T[], key: keyof T, desc = false) {
    return arr.sort((a, b) => {
      const x = a[key];
      const y = b[key];
  
      const res = typeof x === "number" && typeof y === "number"
        ? x - y
        : String(x).localeCompare(String(y));
  
      return desc ? -res : res;
    });
  }