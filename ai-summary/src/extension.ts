// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
import * as vscode from 'vscode';
import * as https from 'node:https';

// This method is called when your extension is activated
// Your extension is activated the very first time the command is executed
export function activate(context: vscode.ExtensionContext) {

	// Use the console to output diagnostic information (console.log) and errors (console.error)
	// This line of code will only be executed once when your extension is activated
	console.log('Congratulations, your extension "ai-summary" is now active!');

	function getTextToSummarize(){
    const editor = vscode.window.activeTextEditor;
    let text = null;
		if (editor) {
			const document = editor.document;
			text = document.getText();
		}
    console.log(text);
    return text;
  }
	let disposable = vscode.commands.registerCommand('ai-summary.helloWorld', (arg) => {
    console.log(arg);
    let postData = getTextToSummarize();
    if (postData === null){
      console.log("No fileis opened, or some cringe occured");
      return;
    }
    postData = JSON.stringify(
      {
        text: postData
      }
    );
    let req = https.request(
      {
        hostname: 'stackoverflow.com',
        port: 8080,
        path: '/',
        method: 'POST',
        headers: {
             'Content-Type': 'application/x-www-form-urlencoded',
             'Content-Length': postData.length
           }
      },
      (res) =>{
        console.log('statusCode:', res.statusCode);
        console.log('headers:', res.headers);

        res.on('data', (d) => {
          process.stdout.write(d);// display result somehow
        });
      }
    );
    req.on('error', (e) => {
      console.error(e);
    });
    req.end();
    
	});

	context.subscriptions.push(disposable);
}

// This method is called when your extension is deactivated
export function deactivate() {}
