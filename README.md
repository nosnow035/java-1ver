# java-1ver
大学の課題で作成したjavaゲームです。

java8以降でjavaFXが使える環境下においてjava -jar game1.jarでゲームが起動するはずです。
それ以外の場合
java -jar game1.jar というコマンドだけでは不十分です。実行時に、JavaFXライブラリ（部品）がどこにあるかを教えてあげる必要があります。
コマンドプロンプトで、以下の形式のコマンドを使って起動してください。
Bash
java --module-path "JavaFX SDKのlibフォルダへのパス" --add-modules javafx.controls,javafx.graphics -jar game1.jar
1. 「JavaFX SDKのlibフォルダへのパス」を見つける
まず、あなたのPCにダウンロードした JavaFX SDK のフォルダを見つけてください。その中にある lib というフォルダの場所が必要です。
一般的なパスの例は以下のようになります（バージョン番号 21 の部分はあなたの環境に合わせてください）。
Windowsの場合:
C:\Program Files\Java\javafx-sdk-21\lib
macOSやLinuxの場合:
/Users/あなたの名前/Downloads/javafx-sdk-21/lib
ポイント: フォルダのパスにスペースが含まれる可能性があるため、パス全体をダブルクォーテーション " で囲むのが安全です。

2. 正しいコマンドで実行する
例えば、あなたのJavaFX SDKのパスが C:\Program Files\Java\javafx-sdk-21\lib だった場合、最終的なコマンドは以下のようになります。
Bash
java --module-path "C:\Program Files\Java\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.graphics -jar game1.jar
このコマンドを game1.jar があるフォルダで実行すれば、JavaFXの部品が正しく読み込まれ、ゲームが起動するはずです。

ゲームを起動する際windowsの設定で表示画面（ディスプレイ）を100パーセントが推奨です。150パーセントではゲーム画面全体が表示できません。

ゲーム1.mp4にゲームの実行のデモ動画があります。

またゲームの説明をpdfにゲーム1.pdfとしてまとめております。

注意が多く申し訳ありません。
