import {spawn} from 'child_process'; 

process.stdin.setRawMode(true);
process.stdin.resume();

const s = spawn('java', ['-cp', '.:./build', 'Main']);
process.stdin.on('data', (c) => {
    s.stdin.write(c);
});
s.stdout.pipe(process.stdout);
s.on('exit', () => {
    process.exit();
});
